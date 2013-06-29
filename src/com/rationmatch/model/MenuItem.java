package com.rationmatch.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class MenuItem extends LinkedHashMap<String, Object> {
  /**
   * 
   */
  private static final long serialVersionUID = 9124762709397289870L;

  public MenuItem() {
    super();
  }

  public MenuItem(JsonNode root) {
    super();
    Iterator<String> entries = root.fieldNames();
    while(entries.hasNext()) {
      String key = entries.next();
      if(root.get(key).isTextual()) {
        put(key, root.get(key).asText());
      } else if(root.get(key).isDouble()) {
        put(key, root.get(key).asDouble());
      } else if(root.get(key).isInt()) {
        put(key, root.get(key).asInt());
      }
    }
  }
  
  public MenuItem(Map<String, Object> map) {
    super(map);
  }

  public String getLabel() {
    return (String)this.get("label");
  }
  
  public void setLabel(String label) {
    this.put("label", label);
  }
}
