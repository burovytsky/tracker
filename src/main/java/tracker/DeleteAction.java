package tracker;

public class DeleteAction implements UserAction {
    @Override
    public String name() {
        return "=== Delete Item ====";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        String id = input.askStr("Enter the id of the item you want to delete: ");
        if (memTracker.delete(id)) {
            System.out.println("Item deleted");
        } else {
            System.out.println("Error, item not found");
        }
        return true;
    }
}
