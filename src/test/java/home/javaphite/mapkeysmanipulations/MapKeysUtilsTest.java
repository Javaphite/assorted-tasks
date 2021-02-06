package home.javaphite.mapkeysmanipulations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapKeysUtilsTest {

    @Test
    void testAllFlatKeysSplittedToMultiLevelMap() {
        String expectedMapContent = "{jvm={memory=10000}, root={host={ip=127.0.0.1, name=myService}}}";
        Map<String, Object> flatMap = new HashMap<>(4);
        flatMap.put("root.host.ip", "127.0.0.1");
        flatMap.put("root.host.name", "myService");
        flatMap.put("root.default.timeout", 100);
        flatMap.put("jvm.memory", 10000);

        Map<String, Object> multiLevelMap = MapKeysUtils.flatMapToMultiLevelMap(flatMap);
        System.out.println("Old map: \n\r" + flatMap);
        System.out.println("New map: \n\r" + multiLevelMap);

        Assertions.assertNotEquals(flatMap.toString(), multiLevelMap.toString());
        Assertions.assertEquals(expectedMapContent, multiLevelMap.toString());
    }
}