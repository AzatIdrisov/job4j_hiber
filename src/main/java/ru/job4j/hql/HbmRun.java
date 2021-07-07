package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
/*

            Candidate one = Candidate.of("Alex", 1, 1000);
            Candidate two = Candidate.of("Nikolay", 2, 2000);
            Candidate three = Candidate.of("Nikita", 3, 3000);

            session.save(one);
            session.save(two);
            session.save(three);
*/
            findAll(session);
            findById(session, 1);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void findAll(Session session) {
        Query query = session.createQuery("from Candidate ");
        for (Object st : query.list()) {
            System.out.println(st);
        }
    }

    public static void findById(Session session, int id) {
        Query query = session.createQuery("from Candidate c where c.id = :fId");
        query.setParameter("fId", id);
        System.out.println(query.uniqueResult());
    }

    public static void findByName(Session session, String name) {
        Query query = session.createQuery("from Candidate c where c.name = :fName");
        query.setParameter("fName", name);
        for (Object st : query.list()) {
            System.out.println(st);
        }
    }

    public static void update(Session session, int id, int exp, int salary) {
        session.createQuery("update Candidate c set c.experience = :newExp, c.salary = :newSalary where c.id = :fId")
                .setParameter("newExp", exp)
                .setParameter("newSalary", salary)
                .setParameter("fId", id)
                .executeUpdate();
    }

    public static void delete(Session session, int id) {
        session.createQuery("delete from Candidate c where c.id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
    }
}
