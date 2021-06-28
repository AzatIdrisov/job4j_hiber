package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibDBInit {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            CarMark toyota = new CarMark("Toyota");
            session.save(toyota);

            CarModel one = new CarModel("Camry", toyota);
            session.save(one);
            CarModel two = new CarModel("Rav-4", toyota);
            session.save(two);
            CarModel three = new CarModel("Corolla", toyota);
            session.save(three);
            CarModel four = new CarModel("Avensis", toyota);
            session.save(four);
            CarModel five = new CarModel("Mark 2", toyota);
            session.save(five);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
