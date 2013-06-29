package com.rationmatch.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;

public final class RdfUtil {
  public static final String LOG_TAG = RdfUtil.class.getSimpleName();
  private static final String XSD_NS = "http://www.w3.org/2001/XMLSchema#";
  private static final String XSD_DECIMAL = XSD_NS+"decimal";
  private static final String XSD_DOUBLE = XSD_NS+"double";
  private static final String XSD_INTEGER = XSD_NS+"integer";
  public static final String US_ARMY_DATA = "http://rationmatch.com";
  public static final String FDA_FOOD_PYRAMID = "http://logd.tw.rpi.edu/source/data-gov/dataset/1294/version/1st-anniversary";
  public static final String USDA_NUTRIENT_DATABASE = "";

  public static final class VariableBinding extends ArrayList<Object> {
    /**
     * 
     */
    private static final long serialVersionUID = -5764559802159393326L;

    protected VariableBinding() {
      super(2);
    }

    protected VariableBinding(String var, String uri) {
      super(2);
      add(var);
      add(uri);
    }

    protected VariableBinding(String var, String value, String dt) {
      super(2);
      add(var);
      if(dt == null) {
        add(value);
      } else if(dt.equals(XSD_INTEGER)) {
        add(Long.parseLong(value));
      } else if(dt.equals(XSD_DOUBLE)) {
        add(Double.parseDouble(value));
      } else if(dt.equals(XSD_DECIMAL)) {
        add(Double.parseDouble(value));
      } else {
        add(value);
      }
    }
  }

  public static final class Solution implements Set<VariableBinding>, Serializable, Cloneable {
    private final Map<String, VariableBinding> backingMap;
    /**
     * 
     */
    private static final long serialVersionUID = -6904508566180785801L;

    protected Solution() {
      backingMap = new HashMap<String, VariableBinding>();
    }

    protected Solution(Solution old) {
      backingMap = new HashMap<String, VariableBinding>(old.backingMap);
    }

    protected Solution(JsonNode solution) {
      backingMap = new HashMap<String, VariableBinding>();
      Iterator<String> i = solution.fieldNames();
      while(i.hasNext()) {
        String var = i.next();
        JsonNode node = solution.get(var);
        if(node.get("type").equals("literal")) {
          backingMap.put(var, new VariableBinding(var, node.get("value").asText(), node.get("datatype").asText()));
        } else {
          backingMap.put(var, new VariableBinding(var, node.get("value").asText()));
        }
      }
    }

    @Override
    public Object clone() {
      return new Solution(this);
    }

    @Override
    public int size() {
      return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
      return backingMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
      if(o == null) {
        return false;
      }
      if(o instanceof VariableBinding) {
        return backingMap.containsKey(((VariableBinding)o).get(0));
      } else {
        return false;
      }
    }

    @Override
    public Iterator<VariableBinding> iterator() {
      return Collections.unmodifiableCollection(backingMap.values()).iterator();
    }

    @Override
    public Object[] toArray() {
      return backingMap.values().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
      return backingMap.values().toArray(a);
    }

    @Override
    public boolean add(VariableBinding e) {
      if(e == null) {
        return false;
      }
      if(!backingMap.containsKey(e.get(0))) {
        return false;
      }
      backingMap.put((String)e.get(0), e);
      return true;
    }

    @Override
    public boolean remove(Object o) {
      if(o == null) {
        return false;
      }
      if(o instanceof VariableBinding) {
        VariableBinding e = (VariableBinding)o;
        if(backingMap.containsKey(e.get(0))) {
          backingMap.remove(e.get(0));
          return true;
        }
      }
      return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      for(Object o : c) {
        if(!contains(o)) {
          return false;
        }
      }
      return true;
    }

    @Override
    public boolean addAll(Collection<? extends VariableBinding> c) {
      return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
      return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      return false;
    }

    @Override
    public void clear() {
      backingMap.clear();
    }

    /**
     * Gets a binding in the solution for the given variable name.
     * @param var
     * @return
     */
    public VariableBinding getBinding(String var) {
      return backingMap.get(var);
    }
  }

  public static JsonNode executeSELECTQuery(String endpoint, String query) {
    Log.d(LOG_TAG, "Executing SPARQL query: "+query);
    ByteArrayOutputStream results = new ByteArrayOutputStream();
    HttpURLConnection conn = null;
    try {
      URI querypath = URI.create(endpoint+"?query="+URLEncoder.encode(query, "UTF-8"));
      conn = (HttpURLConnection)querypath.toURL().openConnection();
    } catch(UnsupportedEncodingException e) {
      return null;
    } catch(IOException e) {
      return null;
    }
    try {
      conn.setDoInput(true);
      conn.connect();
      InputStream is = conn.getInputStream();
      byte[] buffer = new byte[1024];
      int read = 0;
      while((read = is.read(buffer)) > 0) {
        results.write(buffer, 0, read);
      }
      is.close();
    } catch(IOException e) {
      Log.w(LOG_TAG, "Unable to read results from server.");
    }
    JsonNode result = null;
    try {
      result = new ObjectMapper().readTree(results.toByteArray());
    } catch(JsonProcessingException e) {
      Log.w(LOG_TAG, "Error parsing JSON results", e);
    } catch (IOException e) {
      Log.w(LOG_TAG, "Unable to read results from buffer.", e);
    }
    return result;
  }

  /**
   * Converts a ResultSet from a SELECT query into a collection that can
   * be passed to other App Inventor components.
   * @param results
   * @return
   */
  public static Collection<Solution> resultSetAsCollection(JsonNode results) {
    List<Solution> list = new LinkedList<Solution>();
    results = results.get("results").get("bindings");
    for(JsonNode binding : results) {
      list.add(new Solution(binding));
    }
    return list;
  }

  /**
   * Publishes the a semantic web model to the given URI using the SPARQL
   * Graph Protocol. Data will be transmitted using the text/turtle encoding
   * with the UTF-8 charset. The method will return true if the server returns
   * a 2xx response code, otherwise it will return false and log the reason
   * for the failure to logcat.
   * @see http://www.w3.org/TR/2013/REC-sparql11-http-rdf-update-20130321/#http-put
   * @param uri An indirect graph URI (with a graph query parameter)
   * @param model A serialized model containing the RDF content to HTTP PUT
   * @return
   */
  public static boolean publishGraph(URI uri, String model) {
    boolean success = false;
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) uri.toURL().openConnection();
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setRequestMethod("PUT");
      conn.setRequestProperty("Content-Length", Integer.toString(model.length()));
      conn.setRequestProperty("Content-Type", "text/turtle;charset=utf-8");
      conn.connect();
      OutputStream os = conn.getOutputStream();
      PrintStream ps = new PrintStream(os);
      ps.print(model);
      int status = conn.getResponseCode();
      if(status == 200 || status == 201 || status == 204) {
        success = true;
      } else {
        Log.w(LOG_TAG, "Unable to put graph due to HTTP code "+status+" "+conn.getResponseMessage());
      }
      conn.disconnect();
    } catch (MalformedURLException e) {
      Log.w(LOG_TAG, "Unable to publish graph due to malformed URL", e);
    } catch (ProtocolException e) {
      Log.w(LOG_TAG, "Unable to perform HTTP PUT for given URI", e);
    } catch (IOException e) {
      Log.w(LOG_TAG, "Unable to publish graph due to IO failure", e);
    }
    return success;
  }
}
