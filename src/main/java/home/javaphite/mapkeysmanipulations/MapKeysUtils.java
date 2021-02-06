package home.javaphite.mapkeysmanipulations;

import java.util.*;
import java.util.regex.Pattern;

public class MapKeysUtils {
    private static final Pattern FLAT_MAP_KEYS_DELIMITER_PATTERN = Pattern.compile("[.]");

    public static Map<String, Object> flatMapToMultiLevelMap(Map<String, Object> flatMap) {
        Map<String, Object> result = new HashMap<>(flatMap.size());
        Map<String, Map<String, Object>> prefixesRegister = new HashMap<>(flatMap.size());
        flatMap.forEach((k, v) -> result.putAll(toIndentedMultilineKey(k, v, prefixesRegister)));
        return result;
    }

    private static Map<String, Object> toIndentedMultilineKey(String key, Object value,
                                                              Map<String, Map<String, Object>> prefixesRegistry) {
        String[] tokenizedKey = key.split(FLAT_MAP_KEYS_DELIMITER_PATTERN.pattern()); // Future multi-map keys
        Object content = value;
        String actualPrefix = key;
        // Take keys in reverse order to form yaml structure from map leafs to root
        for (int i = tokenizedKey.length - 1; i > 0; i--) {
            // Prefixes for constant navigation in nested maps registry
            actualPrefix = actualPrefix.substring(0, actualPrefix.length() - tokenizedKey[i].length() - 1);
            Map<String, Object> nestedMap = prefixesRegistry.get(actualPrefix);
            if (Objects.nonNull(nestedMap)) {
                // We are at the middle of the tree, nested map already exists
                mergeContent(tokenizedKey[i], content, nestedMap);
                return Collections.emptyMap();
            } else {
                // Leaf level detected, have to create new nested map
                nestedMap = new LinkedHashMap<>(1);
                nestedMap.put(tokenizedKey[i], content);
                prefixesRegistry.put(actualPrefix, nestedMap);
                content = nestedMap;
            }
        }
        // For keys without dots and for top levels of the multi-level keys
        // Top-level maps always merged into result yaml root, so singletonMap is enough here
        Map<String, Object> topLevelMap = new LinkedHashMap<>(1);
        topLevelMap.put(tokenizedKey[0], content);
        prefixesRegistry.put(actualPrefix, topLevelMap);
        return topLevelMap;
    }

    private static void mergeContent(final String key, final Object content, final Map<String, Object> map) {
        if (content instanceof Map) {
            map.putAll((Map<String, Object>) content);
        } else {
            map.put(key, content);
        }
    }
}
