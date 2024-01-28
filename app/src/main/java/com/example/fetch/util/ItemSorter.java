package com.example.fetch.util;

import com.example.fetch.dao.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemSorter {

    public static Map<Integer, List<Item>> groupAndSortItems(List<Item> items) {
        Map<Integer, List<Item>> groupedItems = new HashMap<>();

        for (Item item : items) {
            if (groupedItems.containsKey(item.getListId())) {
                groupedItems.get(item.getListId()).add(item);
            } else {
                List<Item> itemList = new ArrayList<>();
                itemList.add(item);
                groupedItems.put(item.getListId(), itemList);
            }
        }

        // Sort each group by name
        for (List<Item> itemList : groupedItems.values()) {
            itemList.sort(new CustomItemComparator());
        }

        return groupedItems;
    }

    private static class CustomItemComparator implements Comparator<Item> {

        @Override
        public int compare(Item item1, Item item2) {
            int numericPart1 = extractNumericPart(item1.getName());
            int numericPart2 = extractNumericPart(item2.getName());

            return Integer.compare(numericPart1, numericPart2);
        }

        private int extractNumericPart(String itemName) {
            // Extract numeric part from the item name
            Matcher matcher = Pattern.compile("\\d+").matcher(itemName);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            } else {
                return 0; // If no numeric part, consider it as 0
            }
        }
    }
}
