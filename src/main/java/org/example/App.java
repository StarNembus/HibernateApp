package org.example;

import org.example.model.Actor;
import org.example.model.Movie;
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
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // try with resources (try с ресурсами) - ресурс будет закрыт не используя блок finally
        try (sessionFactory){
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Actor actor = session.get(Actor.class, 2);
            System.out.println(actor.getMovies());
            Movie movieToRemovie = actor.getMovies().get(0);

            actor.getMovies().remove(0);
            movieToRemovie.getActors().remove(actor);

            session.getTransaction().commit();
        }
    }
}
