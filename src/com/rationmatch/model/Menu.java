package com.rationmatch.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class Menu extends LinkedHashMap<String, Object> {

  /**
   * 
   */
  private static final long serialVersionUID = -5644768548299292624L;

  public Menu() {
    super();
  }

  public Menu(JsonNode root) {
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
      } else if(root.get(key).isArray()) {
        JsonNode array = root.get(key);
        List<MenuItem> items = new ArrayList<MenuItem>();
        for(JsonNode node : array) {
          items.add(new MenuItem(node));
        }
        put(key, items);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public Menu(Map<String, Object> map) {
    super(map);
    List<Map<String, Object>> items =
        (List<Map<String, Object>>)map.get("hasMenuItems");
    List<MenuItem> items2 = new ArrayList<MenuItem>();
    for(Map<String, Object> child : items) {
      items2.add(new MenuItem(child));
    }
    put("hasMenuItems", items2);
  }

  @SuppressWarnings("unchecked")
  public List<MenuItem> getMenuItems() {
    return (List<MenuItem>)get("hasMenuItems");
  }

  public String getLabel() {
    return (String)get("label");
  }
}
