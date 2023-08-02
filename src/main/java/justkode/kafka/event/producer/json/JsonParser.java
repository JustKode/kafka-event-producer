package justkode.kafka.event.producer.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import justkode.kafka.event.producer.meta.*;

import java.util.*;

public class JsonParser {
    public static Map<String, Meta> getJsonToMetaMap(String jsonStr) {
        Gson gson = new Gson();
        TypeToken<Map<String, Map<String, Object>>> mapType = new TypeToken<Map<String, Map<String, Object>>>(){};

        Map<String, Meta> metaMap = new HashMap<>();
        Map<String, Map<String, Object>> json = gson.fromJson(jsonStr, mapType);

        for (Map.Entry<String, Map<String, Object>> entry: json.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> map = entry.getValue();

            String type = (String) map.get("type");

            switch (type) {
                case "int":
                    metaMap.put(key, IntegerMeta.getMetaByMap(key, map));
                    break;
                case "double":
                    metaMap.put(key, DoubleMeta.getMetaByMap(key, map));
                    break;
                case "float":
                    metaMap.put(key, FloatMeta.getMetaByMap(key, map));
                    break;
                case "long":
                    metaMap.put(key, LongMeta.getMetaByMap(key, map));
                    break;
                case "string":
                    metaMap.put(key, StringMeta.getMetaByMap(key, map));
                    break;
                default:
                    throw new RuntimeException(String.format("'%s' column has not valid type. (type: %s) ", key, type));
            }
        }

        return metaMap;
    }
}
