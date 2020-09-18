package tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection cn;

    public SqlTracker() {
    }

    public SqlTracker(Connection connection) {
        this.cn = connection;
    }

    @Override
    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            createTable();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement st =
                     cn.prepareStatement("insert into item ( name) values (?)", PreparedStatement.RETURN_GENERATED_KEYS);
             ResultSet rs = st.getGeneratedKeys()) {
            st.setString(1, item.getName());
            st.executeUpdate();
            if (rs != null && rs.next()) {
                item.setId((rs.getInt(1)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean rsl = false;
        try (PreparedStatement st = cn.prepareStatement("update item set name = ? where item.id = ?")) {
            st.setString(1, item.getName());
            st.setInt(2, (id));
            if (st.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        boolean rsl = false;
        try (PreparedStatement st = cn.prepareStatement("delete from item WHERE item.id = ?")) {
            st.setInt(1, (id));
            if (st.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public List<Item> findAll() {
        List<Item> itemList = new ArrayList<>();
        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("select * from item")) {
            while (rs.next()) {
                Item item = new Item(rs.getString("name"));
                item.setId(rs.getInt("id"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> rsl = new ArrayList<>();
        try (PreparedStatement st = cn.prepareStatement("select * from item WHERE item.name = ?")) {
            st.setString(1, key);
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    Item item = new Item(resultSet.getString("name"));
                    item.setId(resultSet.getInt("id"));
                    rsl.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Item findById(int id) {
        Item item = null;
        try (PreparedStatement st = cn.prepareStatement("select * from item WHERE item.id = ?")) {
            st.setInt(1, (id));
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    item = new Item(rs.getString("name"));
                    item.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    private void createTable() {
        try (Statement st = cn.createStatement();) {
            st.executeQuery("create table if not exists item (id serial primary key, name varchar(30));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
