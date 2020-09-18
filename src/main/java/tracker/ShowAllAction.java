package tracker;

public class ShowAllAction implements UserAction {
    @Override
    public String name() {
        return "=== Show all items ===";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        for (Item item : memTracker.findAll()) {
            System.out.println(String.format("%s : %d", item.getName(), item.getId()));
        }
        return true;
    }
}
