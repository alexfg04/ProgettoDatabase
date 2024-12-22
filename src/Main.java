import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        // Carica le informazioni di configurazione dal file config.properties (bisogna creare il file config.properties)
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            System.out.println("Sorry, unable to find config.properties");
            ex.printStackTrace();
            return;
        }
        // URL del database
        String url = properties.getProperty("db.url");

        // Credenziali di accesso al database
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        // Connessione al database
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection != null) {
                System.out.println("Connessione al database avvenuta con successo!");
            }
        } catch (SQLException e) {
            System.out.println("Errore nella connessione al database");
            e.printStackTrace();
        }
    }
}