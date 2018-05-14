package mro.utility;

import java.lang.reflect.Field;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {

	public static JsonObject ToJson(String input){
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(input).getAsJsonObject();
	}
	
	public static String ToString(Object object) {
		Gson gson=new Gson();
		return  gson.toJson(object);
	}
	public static Object fromJson(JsonObject json,Class clazz) {
		Gson gson=new Gson();
		return  gson.fromJson(json, clazz);
	}
	public static JsonObject fromObject(Object object) {
		Gson gson=new Gson();
		return  gson.toJsonTree(object).getAsJsonObject();
	}
	public static Object getValue(Object object,String key) {
		try {
			if(object instanceof JSONObject){
				return ((JSONObject)object).get(key);
			}else if(object instanceof JsonObject){
				return ((JsonObject)object).get(key);
			}else if(object instanceof Map){
				return ((Map)object).get(key);
			}else{
				Field field=object.getClass().getDeclaredField(key);
				field.setAccessible(true);
				return field.get(object);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
