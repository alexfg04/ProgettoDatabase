import java.sql.*;
import java.time.LocalDate;

public class Classe {
    private final int codice;
    private final LocalDate inizio;
    private LocalDate fine;
    private LocalDate scadenzaIscrizione;
    private double ricavo;
    private boolean onDatabase;
    private String azienda;

    public Classe(int codice, LocalDate inizio, LocalDate fine, LocalDate scadenzaIscrizione) {
        this.codice = codice;
        this.inizio = inizio;
        this.fine = fine;
        this.scadenzaIscrizione = scadenzaIscrizione;
        this.ricavo = 0;
        this.onDatabase = false;
    }

    protected void caricaSuDatabase(String Azienda) {
        if(isOnDatabase()) {
            return;
        }
        String query = "INSERT INTO Classe (codice, azienda, data_in, data_fine, scadenza_iscrizioni) VALUES (?, ?, ?, ?, ?)";
        // Codice per caricare i dati su un database
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, codice);
            stmt.setString(2, Azienda);
            stmt.setDate(3, Date.valueOf(inizio));
            stmt.setDate(4, Date.valueOf(fine));
            stmt.setDate(5, Date.valueOf(scadenzaIscrizione));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        onDatabase = true;
    }

    public boolean isOnDatabase() {
        return onDatabase;
    }

    public int getCodice() {
        return codice;
    }

    public LocalDate getInizio() {
        return inizio;
    }

    public LocalDate getFine() {
        return fine;
    }

    public void setFine(LocalDate fine) {
        this.fine = fine;
    }

    public LocalDate getScadenzaIscrizione() {
        return scadenzaIscrizione;
    }

    public void setScadenzaIscrizione(LocalDate scadenzaIscrizione) {
        this.scadenzaIscrizione = scadenzaIscrizione;
    }

    public double getRicavo() {
        return ricavo;
    }

    public void setRicavo(double ricavo) {
        this.ricavo = ricavo;
        String query = "UPDATE Classe SET ricavo = ? WHERE codice = ?";
        // Codice per aggiornare il ricavo su un database
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, ricavo);
            stmt.setInt(2, codice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stampaClasse() {
        String query = "SELECT * FROM Classe WHERE codice = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.codice);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                System.out.println("Codice: " + rs.getInt("codice"));
                System.out.println("Azienda: " + rs.getString("azienda"));
                System.out.println("Data inizio: " + rs.getDate("data_in"));
                System.out.println("Data fine: " + rs.getDate("data_fine"));
                System.out.println("Scadenza iscrizioni: " + rs.getDate("scadenza_iscrizioni"));
                System.out.println("Ricavo: " + rs.getDouble("ricavo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void stampaDettagliCorsoConDiscenti() {
        String query = """
            SELECT 
                c.titolo AS titolo_corso,
                SUM(i.n_dipendenti) AS totale_discenti,
                d.descrizione,
                d.mod_erogazione,
                d.settore,
                d.argomenti,
                d.durata,
                d.tipo_servizio
            FROM corso_acatalogo c
            JOIN dettagli_c_acatalogo d ON c.id_C_catalogo = d.id_catalogo
            JOIN classe cl ON c.id_C_catalogo = cl.id_corso
            JOIN iscrizione i ON cl.codice = i.codice_classe
            WHERE cl.codice = ?
            GROUP BY 
                c.titolo,
                d.descrizione,
                d.mod_erogazione,
                d.settore,
                d.argomenti,
                d.durata,
                d.tipo_servizio;
        """;

        try (Connection conn = Database.getConnection(); // Assicurati di avere un metodo Database.getConnection()
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Impostazione del parametro (codice della classe)
            ps.setInt(1, this.codice);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Dettagli del corso:");

                boolean corsiTrovati = false; // Variabile per tracciare se sono stati trovati corsi

                // Itera attraverso i risultati della query
                while (rs.next()) {
                    corsiTrovati = true;

                    // Recupera i dati dal ResultSet
                    String titolo = rs.getString("titolo_corso");
                    int totaleDiscenti = rs.getInt("totale_discenti");
                    String descrizione = rs.getString("descrizione");
                    String modErogazione = rs.getString("mod_erogazione");
                    String settore = rs.getString("settore");
                    String argomento = rs.getString("argomento");
                    int durata = rs.getInt("durata");
                    String tipoServizio = rs.getString("tipo_servizio");

                    // Stampa i dettagli del corso
                    System.out.printf(
                            "Titolo del corso: %s%nTotale discenti: %d%nDescrizione: %s%n" +
                                    "Modalità di erogazione: %s%nSettore: %s%nArgomento: %s%nDurata: %d ore%nTipo di servizio: %s%n",
                            titolo, totaleDiscenti, descrizione, modErogazione, settore,
                            argomento, durata, tipoServizio
                    );
                }

                // Se non sono stati trovati corsi, stampa un messaggio
                if (!corsiTrovati) {
                    System.out.println("Nessun corso trovato per la classe con codice: " + this.codice);
                }
            }

        } catch (SQLException e) {
            // Gestione dell'eccezione in caso di errore durante la query
            throw new RuntimeException("Errore durante l'esecuzione della query.", e);
        }
    }

}
