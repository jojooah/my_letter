package com.jojo.my_letter.model.entity;

import java.util.HashMap;
import java.util.Map;

public class UserContext {
    private static ThreadLocal<Map<String,Object>> userContext = ThreadLocal.withInitial(HashMap::new);

    public static Map<String,Object> getUserContext() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}
