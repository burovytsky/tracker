package tracker;

import java.util.List;

public class FindByNameAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by name ===";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        String name = input.askStr("Enter name: ");
        List<Item> foundItems = memTracker.findByName(name);
        if (foundItems.size() != 0) {
            for (Item item : foundItems) {
                System.out.println(String.format("%s : %d", item.getName(), item.getId()));
            }
        } else {
            System.out.println("No matching items found");
        }
        return true;
    }
}
