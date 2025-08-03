package com.util;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.entity.StudentEntity;

public class HibernateUtil {
    private static SessionFactory sf = null;
    private static StandardServiceRegistry registry = null;

    public static SessionFactory getSessionFactory() {
        if (sf == null) {
            try{
                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3307/atharva");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, "true");
//com.mysql.cj.jdbc.Driver.class
                registry = new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

                MetadataSources mds = new MetadataSources(registry)
                        .addAnnotatedClass(StudentEntity.class);
                Metadata md = mds.getMetadataBuilder().build();
                sf = md.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        
        return sf;
    }
}
