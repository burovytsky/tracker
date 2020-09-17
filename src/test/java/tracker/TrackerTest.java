package tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class TrackerTest {
    @Test
    public void whenExit() {
        List<UserAction> actions = new ArrayList<>();
        StubInput input = new StubInput(
                new String[]{"0"}
        );
        StubAction action = new StubAction();
        actions.add(action);
        new StartUI().init(input, new SqlTracker(), actions);
        assertThat(action.isCall(), is(true));
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("test1");
        memTracker.add(item);
        Item result = memTracker.findById(item.getId());
        assertThat(result.getName(), is(item.getName()));
    }

    @Test
    public void whenFindAllNotNullItems() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("test1");
        Item item1 = new Item("test2");
        Item item2 = new Item("test3");
        memTracker.add(item);
        memTracker.add(item1);
        memTracker.add(item2);
        List<Item> result = memTracker.findAll();
        List<Item> expected = Arrays.asList(item, item1, item2);
        assertThat(result, is(expected));
    }

    @Test
    public void findItemByIdTest() {
        MemTracker memTracker = new MemTracker();
        Item item1 = new Item("test1");
        Item item2 = new Item("test2");
        memTracker.add(item1);
        memTracker.add(item2);
        Item result = memTracker.findById(item2.getId());
        assertThat(result, is(item2));
    }

    @Test
    public void findItemByNameTest() {
        MemTracker memTracker = new MemTracker();
        Item item1 = new Item("test1");
        Item item2 = new Item("test2");
        Item item3 = new Item("test1");
        memTracker.add(item1);
        memTracker.add(item2);
        memTracker.add(item3);

        List<Item> result = memTracker.findByName("test1");
        List<Item> expected = Arrays.asList(item1, item3);
        assertThat(result, is(expected));
    }

    @Test
    public void whenReplace() {
        MemTracker memTracker = new MemTracker();
        Item bug = new Item("Bug");
        memTracker.add(bug);
        String id = bug.getId();
        Item bugWithDesc = new Item("Bug with description");
        memTracker.replace(id, bugWithDesc);
        assertThat(memTracker.findById(id).getName(), is("Bug with description"));
    }

    @Test
    public void whenDelete() {
        MemTracker memTracker = new MemTracker();
        Item bug = new Item("Bug");
        memTracker.add(bug);
        String id = bug.getId();
        memTracker.delete(id);
        assertThat(memTracker.findById(id), is(nullValue()));
    }
}
