package tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SqlTrackerTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            assertThat(tracker.findByName("name").size(), is(1));
        }
    }

    @Test
    public void replace() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            String id = tracker.findByName("name").get(0).getId();
            tracker.replace(id, new Item("newName"));
            assertThat(tracker.findById(id).getName(), is("newName"));
        }
    }

    @Test
    public void delete() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            String id = tracker.findByName("name").get(0).getId();
            tracker.delete(id);
            assertThat(tracker.findAll().size(), is(0));
        }
    }

    @Test
    public void findAll() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("testItem1"));
            tracker.add(new Item("testItem2"));
            tracker.add(new Item("testItem3"));
            System.out.println(tracker.findByName("testItem1"));
            assertThat(tracker.findAll().size(), is(3));
        }
    }

    @Test
    public void findByName() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("test1"));
            assertThat(tracker.findByName("test1").get(0).getName(), is("test1"));
        }
    }

    @Test
    public void findById() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            String id = tracker.findByName("name").get(0).getId();
            assertThat(tracker.findById(id).getName(), is("name"));
        }
    }
}