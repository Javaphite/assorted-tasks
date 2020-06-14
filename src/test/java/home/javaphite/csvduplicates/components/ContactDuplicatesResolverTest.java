package home.javaphite.csvduplicates.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ContactDuplicatesResolverTest {

    @Test
    void shouldReturn111() {
        Map<String, List<String>> inputTable = new HashMap<>();
        inputTable.put("id", List.of("1", "2", "3"));
        List<String> initialParentIds =  new ArrayList<>(3);
        initialParentIds.add("null"); initialParentIds.add("null"); initialParentIds.add("null");
        inputTable.put("parentId", initialParentIds);
        inputTable.put("email", List.of("E1", "E1", "E3"));
        inputTable.put("card", List.of("C1", "C3", "C3"));
        inputTable.put("phone", List.of("P1", "P2", "P3"));

        Map<String, List<String>> outputTable =
                new ContactDuplicatesResolver(inputTable, "id", "parentId").getResolvedTable();

        System.out.println(outputTable);
        Assertions.assertIterableEquals(List.of("1", "1", "1"), outputTable.get("parentId"));
    }

    @Test
    void shouldReturn1111() {
        Map<String, List<String>> inputTable = new HashMap<>();
        inputTable.put("id", List.of("1", "2", "3", "4"));
        List<String> initialParentIds =  new ArrayList<>(4);
        initialParentIds.add("null"); initialParentIds.add("null"); initialParentIds.add("null"); initialParentIds.add("null");
        inputTable.put("parentId", initialParentIds);
        inputTable.put("email", List.of("E1", "E1", "E3", "E4"));
        inputTable.put("card", List.of("C1", "C3", "C3", "C4"));
        inputTable.put("phone", List.of("P1", "P2", "P3", "P3"));

        Map<String, List<String>> outputTable =
                new ContactDuplicatesResolver(inputTable, "id", "parentId").getResolvedTable();

        System.out.println(outputTable);
        Assertions.assertIterableEquals(List.of("1", "1", "1", "1"), outputTable.get("parentId"));
    }

    @Test
    void shouldReturn1133() {
        Map<String, List<String>> inputTable = new HashMap<>();
        inputTable.put("id", List.of("1", "2", "3", "4"));
        List<String> initialParentIds =  new ArrayList<>(4);
        initialParentIds.add("null"); initialParentIds.add("null"); initialParentIds.add("null"); initialParentIds.add("null");
        inputTable.put("parentId", initialParentIds);
        inputTable.put("email", List.of("E1", "E2", "E3", "E4"));
        inputTable.put("card", List.of("C1", "C2", "C3", "C4"));
        inputTable.put("phone", List.of("P1", "P1", "P3", "P3"));

        Map<String, List<String>> outputTable =
                new ContactDuplicatesResolver(inputTable, "id", "parentId").getResolvedTable();

        System.out.println(outputTable);
        Assertions.assertIterableEquals(List.of("1", "1", "3", "3"), outputTable.get("parentId"));
    }
}