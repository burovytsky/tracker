package tracker;

public class CreateAction implements UserAction {
    @Override
    public String name() {
        return  "=== Create a new Item ====";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        String name2 = input.askStr("Enter name: ");
        Item item = new Item(name2);
        memTracker.add(item);
        return true;
    }
}
