package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Person person = session.get(Person.class, 3);
            // получаем список всех товаров человека
            List<Item> items = person.getItems();
            // SQL
            // проходимся по списку и удаляем все товары человека с id=3
            for (Item item:items) {
                session.remove(item);
            }
            // Не порождает SQL, но необходимо для того, чтобы в кэше все было верно
            person.getItems().clear();

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
