package tracker;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "=== Exit ===";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        return false;
    }
}