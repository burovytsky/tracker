package tracker;

public class EditAction implements UserAction {
    @Override
    public String name() {
        return "=== Edit Item ===";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        String id = input.askStr("Enter the id of the item you want to edit: ");
        String name = input.askStr("Enter new name: ");
        Item item = new Item(name);
        if (memTracker.replace(id, item)) {
            System.out.println("Successfully changed");
        } else {
            System.out.println("Error, item not found");
        }
        return true;
    }
}
