import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSON2CSV {

  public static String readFile(File f) {
    try {
      byte[] buffer = new byte[4096];
      int read = 0;
      InputStream is = new FileInputStream(f);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      while((read = is.read(buffer)) > 0) {
        baos.write(buffer, 0, read);
      }
      is.close();
      return baos.toString("UTF-8");
    } catch(IOException e) {
      return null;
    }
  }

  public static String[] jsonToArray(JSONArray arr) {
    String[] items = new String[arr.length()];
    for(int i=0; i<arr.length(); i++) {
      items[i] = arr.optString(i);
    }
    return items;
  }

  public static void main(String[] args) throws JSONException, IOException {
    JSONArray entries = new JSONArray(readFile(new File(args[0])));
    PrintStream os = new PrintStream(new FileOutputStream(args[1]));
    String[] cols = null;
    for(int i=0; i<entries.length(); i++) {
      JSONObject entry = entries.getJSONObject(i);
      if( cols == null ) {
        cols = jsonToArray(entry.names());
        writeArray(os, cols);
      }
      writeObject(os, cols, entry);
    }
    os.close();
  }

  public static void writeArray(PrintStream os, String[] cols) {
    for(int i=0; i<cols.length; i++) {
      if(i != 0) {
        os.print(",");
      }
      os.print(cols[i]);
    }
    os.println();
  }

  public static void writeObject(PrintStream os, String[] cols, JSONObject entry) {
    for(int i=0; i<cols.length; i++) {
      if(i != 0) {
        os.print(",");
      }
      String text = entry.optString(cols[i]);
      if(text.contains(",")) {
        System.out.println("found a comma");
        text = "\""+text.replaceAll(", ", ">")+"\"";
      }
      if(text.contains("%")) {
        System.out.println(text);
        text = text.substring(0, text.length()-1);
      }
      os.print(text);
    }
    os.println();
  }
}
