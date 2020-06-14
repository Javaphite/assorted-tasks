package home.javaphite.csvduplicates.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactDuplicatesResolver {

    Map<String, ContactGroup> keyFieldsToContactGroup;
    Map<String, List<String>> columns;
    String idColumn;
    String parentIdColumn;

    public ContactDuplicatesResolver(Map<String, List<String>> columns, String idColumn, String parentIdColumn) {
        this.columns = columns;
        this.idColumn = idColumn;
        this.parentIdColumn = parentIdColumn;
        this.keyFieldsToContactGroup = new HashMap<>();
    }

    private void process(String id) {
       Integer parsedId = Integer.parseInt(id);
       String email = columns.get("email").get(parsedId-1);
       String card = columns.get("card").get(parsedId-1);
       String phone = columns.get("phone").get(parsedId-1);

       ContactGroup group = null;
       if (keyFieldsToContactGroup.containsKey(email)) {
           group = keyFieldsToContactGroup.get(email).mergeGroup(group);
           keyFieldsToContactGroup.put(email, group);
           keyFieldsToContactGroup.put(card, group);
           keyFieldsToContactGroup.put(phone, group);
       }

        if (keyFieldsToContactGroup.containsKey(card)) {
            group = keyFieldsToContactGroup.get(card).mergeGroup(group);
            keyFieldsToContactGroup.put(email, group);
            keyFieldsToContactGroup.put(card, group);
            keyFieldsToContactGroup.put(phone, group);
        }

        if (keyFieldsToContactGroup.containsKey(phone)) {
            group = keyFieldsToContactGroup.get(phone).mergeGroup(group);
            keyFieldsToContactGroup.put(email, group);
            keyFieldsToContactGroup.put(card, group);
            keyFieldsToContactGroup.put(phone, group);
        }

        if (group == null) {
            group = new ContactGroup(parsedId);
            keyFieldsToContactGroup.put(email, group);
            keyFieldsToContactGroup.put(card, group);
            keyFieldsToContactGroup.put(phone, group);
        }
    }

    private void setParentId(String id) {
       String key = columns.get("email").get(Integer.parseInt(id)-1);
       columns.get(parentIdColumn).set(Integer.parseInt(id)-1,
                String.valueOf(keyFieldsToContactGroup.get(key).parentId));
    }

    public Map<String, List<String>> getResolvedTable() {
        columns.get(idColumn).forEach(this::process);
        columns.get(idColumn).forEach(this::setParentId);
        return columns;
    }

    private static class ContactGroup {
        private int parentId;

        ContactGroup(int initialParentId) {
            this.parentId = initialParentId;
        }

        ContactGroup mergeGroup(ContactGroup anotherGroup) {
            if (anotherGroup == null || anotherGroup == this) {
                return this;
            }
            parentId = Math.min(anotherGroup.parentId, parentId);
            anotherGroup.parentId = parentId;
            return this;
        }
    }
}
