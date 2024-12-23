import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final String PROPERTIES_FILE = "config.properties";
    private static String url;
    private static String username;
    private static String password;

    // Blocco statico per caricare le configurazioni una sola volta
    static {
        try {
            loadProperties();
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento del file di configurazione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo per caricare le configurazioni
    private static void loadProperties() throws IOException {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        }
    }

    // Metodo statico per ottenere la connessione al database
    public static Connection getConnection() throws SQLException {
        if (url == null || username == null || password == null) {
            throw new IllegalStateException("Configurazione del database non caricata correttamente.");
        }
        return DriverManager.getConnection(url, username, password);
    }
}
