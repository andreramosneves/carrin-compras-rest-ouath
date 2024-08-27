package org.com.br.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    
    //emf
    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                // so destroy it manually.
                StandardServiceRegistryBuilder.destroy(registry);
            }
            return sessionFactory;
        } else {
            return sessionFactory;
        }
    }

    public static void shutdown() {
        sessionFactory.close();
    }

    public static void main(String[] args) {
        HibernateUtil.createSessionFactory();
    }

}
