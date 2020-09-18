package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemTracker implements Store {
    private final List<Item> items = new ArrayList<>();

    @Override
    public void init() {

    }

    public Item add(Item item) {
        item.setId(generateId());
        items.add(item);
        return item;
    }

    public List<Item> findAll() {
        return items;
    }

    public List<Item> findByName(String key) {
        List<Item> itemsMatchingByName = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                itemsMatchingByName.add(item);
            }
        }
        return itemsMatchingByName;
    }

    private int generateId() {
        Random rm = new Random();
        return (int) (rm.nextLong() + System.currentTimeMillis());
    }

    public Item findById(int id) {
        Item rsl = null;
        int index = indexOfElement(id);
        if (index != -1) {
            rsl = items.get(index);
        }
        return rsl;
    }

    public boolean replace(int id, Item item) {
        int index = indexOfElement(id);
        boolean rsl = false;
        if (index != -1) {
            item.setId(items.get(index).getId());
            items.set(index, item);
            rsl = true;
        }
        return rsl;
    }

    public boolean delete(int id) {
        int index = indexOfElement(id);
        boolean rsl = false;
        if (index != -1) {
            items.remove(index);
            rsl = true;
        }
        return rsl;
    }

    private int indexOfElement(int id) {
        int rsl = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == (id)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    @Override
    public void close() {

    }
}