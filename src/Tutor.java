import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tutor {
    private String codiceFiscale;
    private String nome;
    private String cognome;

    public Tutor(String codiceFiscale, String nome, String cognome) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }




    // Metodo per eseguire la query e stampare il docente con il maggiore numero di impieghi
    public static void stampaConImpiegoMassimo() {
        // La query SQL per ottenere il docente con il massimo totale di impieghi
        String query = """
            SELECT dt.cf AS codice_fiscale,
                   dt.nome,
                   dt.cognome,
                   (IFNULL(cp.num_corsi, 0) + IFNULL(coll.num_classi, 0)) AS totale_impieghi
            FROM docenti_tutor dt
            LEFT JOIN (
                SELECT tutor, COUNT(*) AS num_corsi
                FROM corso_personalizzato
                GROUP BY tutor
            ) cp ON dt.cf = cp.tutor
            LEFT JOIN (
                SELECT persona, COUNT(*) AS num_classi
                FROM Collaborazione
                GROUP BY persona
            ) coll ON dt.cf = coll.persona
            ORDER BY totale_impieghi DESC
            LIMIT 1;
        """;

        // Connessione al database e preparazione della query
        try (Connection conn = Database.getConnection(); // Assicurati di avere un metodo Database.getConnection()
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Esecuzione della query
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Recupera i dati delle colonne dalla query
                    String codiceFiscale = rs.getString("codice_fiscale");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    int totaleImpiegni = rs.getInt("totale_impieghi");

                    // Stampa i dettagli del docente
                    System.out.printf("Codice Fiscale: %s, Nome: %s, Cognome: %s, Totale Impieghi: %d%n",
                            codiceFiscale, nome, cognome, totaleImpiegni);
                } else {
                    // Se non ci sono risultati
                    System.out.println("Nessun docente trovato.");
                }
            }

        } catch (SQLException e) {
            // Gestione dell'eccezione in caso di errore durante la query
            throw new RuntimeException("Errore durante l'esecuzione della query.", e);
        }
    }

    public static void verificaNonCoinvolti() {
        boolean tutorNonCoinvolti = false;
        String query = "SELECT T.cf, T.nome, T.cognome FROM docenti_tutor T " +
                "LEFT JOIN corso_personalizzato C ON T.cf = C.tutor\n" +
                "LEFT JOIN collaborazione CL ON T.cf = CL.persona\n" +
                "WHERE C.tutor IS NULL AND CL.persona IS NULL;";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Tutor non coinvolti in corsi:");

            while (rs.next()) {
                tutorNonCoinvolti = true;
                String codiceFiscale = rs.getString("cf");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                System.out.printf("Codice Fiscale: %s - Nome: %s - Cognome: %s%n", codiceFiscale, nome, cognome);
            }

            if (!tutorNonCoinvolti) {
                System.out.println("Tutti i tutor sono attualmente coinvolti in corsi.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la verifica dei tutor non coinvolti in corsi.", e);
        }
    }
}
