package home.javaphite.csvduplicates.components;

import java.util.*;

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

    private void connectWithRelatedContacts(String id) {
        Integer parsedId = Integer.parseInt(id);
        String email = columns.get("email").get(parsedId - 1);
        String card = columns.get("card").get(parsedId - 1);
        String phone = columns.get("phone").get(parsedId - 1);

        // Check all groups that related to this contact row
        ContactGroup group = null;
        List<ContactGroup> groupsToMerge = new ArrayList<>(3);
        if (keyFieldsToContactGroup.containsKey(email)) { // related by email?
            group = keyFieldsToContactGroup.get(email);
            groupsToMerge.add(group);
        }

        if (keyFieldsToContactGroup.containsKey(card)) { // related by card?
            group = keyFieldsToContactGroup.get(card);
            groupsToMerge.add(group);
        }

        if (keyFieldsToContactGroup.containsKey(phone)) { // related by phone?
            group = keyFieldsToContactGroup.get(phone);
            groupsToMerge.add(group);
        }

        if (group == null) { // no existing related groups found, lets create new one for this individual row
            group = new ContactGroup(parsedId);
        }

        // merge all related groups we found in previous steps, if only 1 related group found -> no merge required
        for (int i = 1; i < groupsToMerge.size(); i++) {
            groupsToMerge.get(0).mergeGroup(groupsToMerge.get(i));
        }

        // finally, lets associate all key fields of this contact row with contact group
        // it maybe any group from related we found in previous steps, as we recently merged all of them
        keyFieldsToContactGroup.put(email, group);
        keyFieldsToContactGroup.put(card, group);
        keyFieldsToContactGroup.put(phone, group);
        // PROFIT!!!
    }

    private void updateParentId(String id) {
        String key = columns.get("email").get(Integer.parseInt(id) - 1);
        columns.get(parentIdColumn).set(Integer.parseInt(id) - 1,
                String.valueOf(keyFieldsToContactGroup.get(key).parentId));
    }

    public Map<String, List<String>> getResolvedTable() {
        columns.get(idColumn).forEach(this::connectWithRelatedContacts);
        columns.get(idColumn).forEach(this::updateParentId);
        return columns;
    }

    private static class ContactGroup {
        private int parentId;

        ContactGroup(int initialParentId) {
            this.parentId = initialParentId;
        }

        // ContactGroup can be replaced with 1-dimensional array, where:
        // index -> define separate group, value by index -> current parentId for this group
        // merge() in that case should be utility method that takes 2 indices: 1st group and 2nd group to merge
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
