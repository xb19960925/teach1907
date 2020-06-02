package com.teach.frame.utils;

import java.util.HashMap;


public class ParamHashMap extends HashMap<String,Object> {

   public ParamHashMap add(String key,Object value){
       this.put(key,value);
       return this;
   }
}
