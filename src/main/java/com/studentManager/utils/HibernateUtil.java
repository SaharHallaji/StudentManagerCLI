package com.studentManager.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.studentManager.models.CourseModel;
import com.studentManager.models.DepartmentModel;
import com.studentManager.models.ProfessorModel;
import com.studentManager.models.StudentModel;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static String url;
    private static String username;
    private static String password;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(StudentModel.class);
                configuration.addAnnotatedClass(CourseModel.class);
                configuration.addAnnotatedClass(DepartmentModel.class);
                configuration.addAnnotatedClass(ProfessorModel.class);

                StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());

                sessionFactory = configuration.buildSessionFactory(serviceRegistry.build());
            } catch (Exception e) {
                System.out.println("something went wrong with hibernate.");
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        readConfigurations();
        Configuration configuration = new Configuration();

        // Hibernate settings
        Properties settings = new Properties();
        settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        settings.put("hibernate.connection.url", url + "?useSSL=false");
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.agroal.maxSize","20");
        settings.put("hibernate.hbm2ddl.auto", "update");

        configuration.setProperties(settings);
        return configuration;
    }

    private static void readConfigurations() {
        try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find hibernate.properties");
                return;
            }

            prop.load(input);
            url = prop.getProperty("hibernate.connection.url");
            username = prop.getProperty("hibernate.connection.username");
            password = prop.getProperty("hibernate.connection.password");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}