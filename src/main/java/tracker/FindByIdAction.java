package tracker;

public class FindByIdAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by ID ===";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        String id = input.askStr("Enter id to search: ");
        Item desiredObject = memTracker.findById(id);
        if (desiredObject != null) {
            System.out.println(desiredObject.getName());
        } else {
            System.out.println("Item not found");
        }
        return true;
    }
}
