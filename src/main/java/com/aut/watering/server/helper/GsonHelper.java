package com.aut.watering.server.helper;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonHelper {

	private Gson gsonInstance;

	public GsonHelper(){
		gsonInstance = new Gson();
	}
	
	public <T> T toClass (String json, Class<T> t){
		return gsonInstance.fromJson(json, t); 		
	}

	public <T> String fromClass (Class<T> obj){
		return gsonInstance.toJson(obj, obj.getClass());
	}

	public <T> List<T> toClassList (String jsonList, Class<T> t){
		t.getClass();
		return gsonInstance.fromJson(jsonList, new TypeToken<List<T>>(){}.getType());
	}

}
