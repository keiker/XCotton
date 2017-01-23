package org.jboss.modules;

import java.util.Map;

public class MapUtils {
	public static <T, U> U getOrDefault(Map<T, U> map, T k, U dft) {
		U v = null;
		if(map != null) {
			v = map.get(k);
		}
		
		if(v == null) {
			v = dft;
		}
		
		return v;
	}
}
