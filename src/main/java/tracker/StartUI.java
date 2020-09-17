package tracker;

import java.util.ArrayList;
import java.util.List;

public class StartUI {

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Input validateInput = new ValidateInput(input);
        try (Store memTracker = new SqlTracker()) {
            memTracker.init();
            List<UserAction> actions = new ArrayList<>();
            actions.add(new CreateAction());
            actions.add(new ShowAllAction());
            actions.add(new EditAction());
            actions.add(new DeleteAction());
            actions.add(new FindByIdAction());
            actions.add(new FindByNameAction());
            actions.add(new ExitAction());
            new StartUI().init(validateInput, memTracker, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(Input input, Store memTracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select ", actions.size());
            UserAction action = actions.get(select);
            run = action.execute(input, memTracker);
        }
    }

    private void showMenu(List<UserAction> actions) {
        System.out.println("Menu.");
        int i = 0;
        for (UserAction userAction : actions) {
            System.out.println(i + ". " + userAction.name());
            i++;
        }
    }
}
