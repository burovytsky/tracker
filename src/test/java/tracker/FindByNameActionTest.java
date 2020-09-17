package tracker;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FindByNameActionTest {

    @Test
    public void findByNameTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));
        MemTracker memTracker = new MemTracker();
        Item item = new Item("item");
        memTracker.add(item);
        FindByNameAction action = new FindByNameAction();
        action.execute(new StubInput(new String[]{item.getName()}), memTracker);
        String expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add(item.getName() + " : " + item.getId())
                .toString();
        assertThat(new String(out.toByteArray()), is(expected));
        System.setOut(def);
    }
}
