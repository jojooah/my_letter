package com.jojo.my_letter.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class Utils {

    private static ObjectMapper mObjectMapper = null;

    public static ObjectMapper getObjectMapper() {
        if (mObjectMapper == null) {
            mObjectMapper = new ObjectMapper();
            mObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mObjectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            mObjectMapper.registerModule(new JavaTimeModule());
            mObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return mObjectMapper;
    }

    /**
     * 주어진 객체가 null이거나 값이 비어있는지 검사한다.
     * http://stove99.tistory.com/73 참조
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {

        if (obj instanceof String) {
            return obj == null || "".equals(obj.toString().trim());
        } else if (obj instanceof List) {
            return obj == null || ((List<?>) obj).isEmpty();
        } else if (obj instanceof Map) {
            return obj == null || ((Map<?,?>) obj).isEmpty();
        } else if (obj instanceof Object[]) {
            return obj == null || Array.getLength(obj) == 0;
        } else {
            return obj == null;
        }
    }

    public static boolean isNotEmpty(String s) {

        return !isEmpty(s);
    }

    public static boolean isContainsKorean(String message) {
        return message.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    }

    /**
     * MessageFormat 클래스를 사용하여 지정된 메지시를 형식화 한다.
     * @param format
     * @param args
     * @return
     */
    public static String fm(String format, Object...args) {
        return MessageFormat.format(format, args);
    }

    /**
     * 이메일 ID에서 '.'과 '+' 문자를 제거한다.
     *
     * @param email
     * @return
     */
    public static String normalizeEmail(String email) {

        String user = email.split("@")[0];
        String domain = email.substring(email.indexOf('@'));
        user = user.replaceAll("\\.", "");
        if (user.contains("+")) {
            user = user.substring(0, user.indexOf("+"));
        }
        return user + domain;
    }

    /**
     * 주어진 문자열이 유효한 JSON 문자열인지 알려준다.<br>
     * <a href="http://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java">
     *     stackoverflow : how-to-check-whether-a-given-string-is-valid-json-in-java</a>
     *
     * @param json
     * @return
     */
    public static boolean isValidJSON(final String json) {

        try {
            final ObjectMapper om = new ObjectMapper();
            om.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String toJson(Object object) {

        ObjectMapper objectMapper = getObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String toJsonPretty(Object object) {

        ObjectMapper objectMapper = getObjectMapper();
        String json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static Map<String, String> toMapFromJson(String json) {

        ObjectMapper objectMapper = getObjectMapper();
        Map<String, String> ret = new LinkedHashMap<>();
        try {
            ret = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * ARS 또는 이메일에서 사용할 인증 코드를 발생시킨다.
     * @return
     */
    public static final String generateOtp() {

        return generateOtp(6);
    }

    public static final String generateOtp(int length) {

        StringBuffer sb = new StringBuffer();
        int x = 0;
        while (x == 0) {
            x = (int) (Math.random() * 10); // 0이 아닌 숫자 발생
        }
        sb.append(x);
        for (int i = 0; i < length-1; i++) {
            x = (int) (Math.random() * 10);
            sb.append(x);
        }
        return sb.toString();
    }

    public static <T> T toObject(Object object, Class<T> clazz) {

        ObjectMapper objectMapper = getObjectMapper();
        T obj = null;
        try {
            obj = objectMapper.readValue(toJson(object), clazz);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return obj;
    }

    public static <T> T toObject(String json, Class<T> clazz) {

        ObjectMapper objectMapper = getObjectMapper();
        T obj = null;
        try {
            obj = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return obj;
    }

    public static <T> T convertValue(Object object, TypeReference<T> clazz) {

        ObjectMapper objectMapper = getObjectMapper();
        T obj = objectMapper.convertValue(object, clazz);
        return obj;
    }

    /**
     * 자바 오브젝트를 맵으로 변환<br>
     * <a href="http://erictus.tistory.com/entry/Map-to-Object-%EC%99%80-Object-to-Map">http://erictus.tistory.com/entry/Map-to-Object-%EC%99%80-Object-to-Map</a>
     * @param obj
     * @return
     */
    public static Map<String, Object> toMap(Object obj) {

        Map<String, Object> result = new HashMap<>();
        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    result.put(pd.getName(), reader.invoke(obj));
                }
            }
        } catch (Exception e) {
            result.put(obj.getClass().getName(), obj);
        }
        return result;
    }

}