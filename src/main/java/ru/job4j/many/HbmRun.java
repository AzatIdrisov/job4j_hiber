package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<CarModel> models = new ArrayList<>();
            models.add( CarModel.of("Camry"));
            models.add( CarModel.of("Rav-4"));
            models.add( CarModel.of("Corolla"));
            models.add( CarModel.of("Avensis"));
            models.add( CarModel.of("Mark 2"));
            models.forEach(session::save);

            CarCompany company = CarCompany.of("Toyota");
            for (int i = 1; i <= 5; i++) {
                company.addModel(session.load(CarModel.class, i));
            }

            session.save(company);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}