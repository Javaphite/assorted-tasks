package home.javaphite.hashtesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class HashFunctionUtilsTest {

    private static final String[] stringComponentOptions = {"option1", "option2", "oPTion3", null};
    private static final Class<?>[] classComponentOptions = {String.class, Integer.class, HashFunctionUtils.class, null};
    private static final List<EnumComponent> enumComponentOptions = new ArrayList<>(EnumComponent.values().length + 1);

    private static List<List<Object>> testCases;

    @BeforeAll
    public static void initTestCases() {
        for (EnumComponent enumComponent : EnumComponent.values()) {
            enumComponentOptions.add(enumComponent);
        }
        enumComponentOptions.add(null);
        int casesNum = stringComponentOptions.length * classComponentOptions.length * EnumComponent.values().length;
        testCases = new ArrayList<>(casesNum);
        for (String stringComponent : stringComponentOptions) {
            for (Class<?> classComponent : classComponentOptions) {
                for (EnumComponent enumComponent : enumComponentOptions) {
                    List<Object> listOfNullables = new ArrayList<>(3);
                    listOfNullables.add(stringComponent == null ? null : stringComponent.toLowerCase());
                    listOfNullables.add(classComponent);
                    listOfNullables.add(enumComponent);
                    testCases.add(listOfNullables);
                }
            }
        }
    }

    @Test
    void testGroupHashingFunctionForCollisions() {
        int collisionCounter = 0;
        Set<Integer> triesCollector = new HashSet<>(testCases.size());
        for (List<Object> testCase : testCases) {
            if (!triesCollector.add(HashFunctionUtils.getGroupHash(testCase.toArray()))) {
                collisionCounter++;
            }
        }
        Assertions.assertTrue(collisionCounter <= 0,
                "Expected to produce no collisions, but get " + collisionCounter);
    }

    @Test
    void testGroupHashingFunctionOrderInvariance() {
        int invariant1 = HashFunctionUtils.getGroupHash(stringComponentOptions[0], classComponentOptions[0], enumComponentOptions.get(0));
        int invariant2 = HashFunctionUtils.getGroupHash(stringComponentOptions[0], enumComponentOptions.get(0), classComponentOptions[0]);
        int invariant3 = HashFunctionUtils.getGroupHash(classComponentOptions[0], stringComponentOptions[0], enumComponentOptions.get(0));

        Assertions.assertEquals(invariant1, invariant2);
        Assertions.assertEquals(invariant2, invariant3);
    }

    private enum EnumComponent {
        OPTION1, OPTION2, OPTION3, OPTION4, OPTION5, OPTION6, OPTION7, OPTION8, OPTION9, OPTION10;
    }

}