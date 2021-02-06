package home.javaphite.hashtesting;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static home.javaphite.hashtesting.HashFunctionUtils.getGroupHash;

public class MultiKeyMapTest {

    @Test
    void testApacheCommonsMultiKeyMapOrderInvariance() {
        MultiKeyMap<Object, Object> multiKeyMap = new MultiKeyMap<>();
        multiKeyMap.put("StringKeyPart", MultiKeyMapTest.class, 100500, 100);

        Assertions.assertNull(multiKeyMap.get("StringKeyPart", MultiKeyMapTest.class));
        Assertions.assertEquals(100, multiKeyMap.get("StringKeyPart", MultiKeyMapTest.class, 100500));
        Assertions.assertEquals(100, multiKeyMap.get(100500, "StringKeyPart", MultiKeyMapTest.class),
                "Order invariance not supported!");
    }

    @Test
    void testSimpleMapOrderInvariance() {
        Map<Integer, Object> multiKeyMap = new HashMap<>();
        multiKeyMap.put(getGroupHash("StringKeyPart", MultiKeyMapTest.class, 100500), 100);

        Assertions.assertNull(multiKeyMap.get(getGroupHash("StringKeyPart", MultiKeyMapTest.class)));
        Assertions.assertEquals(100,
                multiKeyMap.get(getGroupHash("StringKeyPart", MultiKeyMapTest.class, 100500)));
        Assertions.assertEquals(100,
                multiKeyMap.get(getGroupHash(100500, "StringKeyPart", MultiKeyMapTest.class)),
                "Order invariance not supported!");
    }
}
