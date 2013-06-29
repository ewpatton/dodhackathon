package com.rationmatch.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MenuCollectionFactory {
  private static Map<String, List<Menu>> menuClasses;

  public static List<Menu> listMenusInCollection(Context context, String collection) {
    if(menuClasses == null) {
      initialize(context);
    }
    return menuClasses.get(collection);
  }
  
  public static List<String> listMenuCollections(Context context) {
    if(menuClasses == null) {
      initialize(context);
    }
    return new ArrayList<String>(menuClasses.keySet());
  }
  
  protected static void initialize(Context context) {
    menuClasses = new LinkedHashMap<String, List<Menu>>();
    File data = context.getDir("data", Context.MODE_PRIVATE);
    File target = new File(data, "database.json");
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(target);
      if(!root.isArray()) return;
      for(int i=0;i<root.size();i++) {
        JsonNode menuClass = root.get(i);
        String menuClassName = menuClass.get("label").asText();
        JsonNode meals = menuClass.get("hasMeals");
        List<Menu> menus = new ArrayList<Menu>();
        for(JsonNode meal : meals) {
          menus.add(new Menu(meal));
        }
        menuClasses.put(menuClassName, menus);
      }
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
