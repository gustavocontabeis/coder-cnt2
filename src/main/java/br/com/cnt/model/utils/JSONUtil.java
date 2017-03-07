package br.com.cnt.model.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.coder.arqprime.model.entity.BaseEntity;

public class JSONUtil {
	
	private static Gson gson;
	
	static{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.setPrettyPrinting().create();
	}

	public static String toJSON(BaseEntity imovel) {
		return gson.toJson(imovel);
	}

	public static Object toObject(String json, Class<?> classe) {
		return gson.fromJson(json, classe);
	}

}
