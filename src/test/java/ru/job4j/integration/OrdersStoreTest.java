package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void sutUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        execute("./db/update_001.sql");
    }

    @After
    public void cleanUp() throws SQLException {
        execute("./db/delete_001.sql");
    }

    private void execute(String source) throws SQLException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(source)))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderUpdateAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        Order order = Order.of("name1", "description1");
        store.save(order);

        Order newOrder = Order.of("newOrder", "newDesc");

        newOrder.setId(order.getId());
        store.update(newOrder);
        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("newDesc"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrdersAndFindByNameOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        Order order1 = Order.of("name1", "description1");
        store.save(order1);

        Order order2 = Order.of("name1", "description2");
        store.save(order2);

        List<Order> all = (List<Order>) store.findByName("name1");

        assertThat(all.size(), is(2));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
        assertThat(all.get(1).getDescription(), is("description2"));
        assertThat(all.get(1).getId(), is(2));
    }

    @Test
    public void whenSaveOrdersAndFindByNameOnlyOneOrder() {
        OrdersStore store = new OrdersStore(pool);

        Order order1 = Order.of("name1", "description1");
        store.save(order1);

        Order order2 = Order.of("name2", "description2");
        store.save(order2);

        List<Order> all = (List<Order>) store.findByName("name1");

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }
}