package com.github.zachdeibert.operationmanipulation.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionUtils {
    @NonNull
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> checkedAssignment(@NonNull ArrayList list, @NonNull Class<T> cls) {
        ArrayList<T> realList = new ArrayList<>();
        for (Object obj : list) {
            if (cls.isAssignableFrom(obj.getClass())) {
                realList.add((T) obj);
            } else {
                Log.e("CollectionUtils", "Invalid list element");
            }
        }
        return realList;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> checkedAssignment(@NonNull HashMap map, @NonNull Class<K> clsK, @NonNull Class<V> clsV) {
        HashMap<K, V> realMap = new HashMap<>();
        for (Object key : map.keySet()) {
            if (clsK.isAssignableFrom(key.getClass())) {
                Object value = map.get(key);
                if (clsV.isAssignableFrom(value.getClass())) {
                    realMap.put((K) key, (V) value);
                } else {
                    Log.e("CollectionUtils", "Invalid map value");
                }
            } else {
                Log.e("CollectionUtils", "Invalid map key");
            }
        }
        return realMap;
    }
}
