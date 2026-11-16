package ma.projet.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    private HibernateUtil() {}

    public static SessionFactory fromApplicationProperties(Class<?>... entities) {
        Properties properties = new Properties();
        try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) throw new IllegalStateException("application.properties introuvable");
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de lire application.properties", e);
        }
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (url != null) properties.setProperty("hibernate.connection.url", url);
        if (user != null) properties.setProperty("hibernate.connection.username", user);
        if (password != null) properties.setProperty("hibernate.connection.password", password);
        return createSessionFactory(properties, entities);
    }

    public static SessionFactory createSessionFactory(Properties properties, Class<?>... entities) {
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        for (Class<?> entity : entities) configuration.addAnnotatedClass(entity);
        return configuration.buildSessionFactory();
    }
}
