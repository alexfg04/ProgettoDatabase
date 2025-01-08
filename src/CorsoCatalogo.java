import java.sql.*;

public class CorsoCatalogo extends Corso {
    private String settore;
    private String argomento;
    private double costoAPersona;
    private boolean onDatabase;
    private int durata;

    public CorsoCatalogo(String titolo, String settore, String descrizione, String argomento, double costoAPersona, ModalitaErogazione modalita, TipoServizio tipoServizio, int durata) {
        super(titolo, descrizione, modalita, tipoServizio);
        this.settore = settore;
        this.argomento = argomento;
        this.costoAPersona = costoAPersona;
        this.onDatabase = false;
        this.durata = durata;
    }

    protected void caricaSuDatabase(String pIvaAzienda) {
        try (Connection conn = Database.getConnection()) {
            // Inserisci il corso nella tabella Corso_ACatalogo
            String queryCorso = "INSERT INTO Corso_ACatalogo (titolo, id_azienda) VALUES (?, ?)";
            try (PreparedStatement stmtCorso = conn.prepareStatement(queryCorso, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtCorso.setString(1, titolo);
                stmtCorso.setString(2, pIvaAzienda);
                stmtCorso.executeUpdate();

                // Ottieni l'ID generato per il corso
                try (ResultSet generatedKeys = stmtCorso.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int corsoId = generatedKeys.getInt(1);
                        inserisciDettagliCorso(corsoId);
                        onDatabase = true;
                    } else {
                        throw new SQLException("Creazione corso fallita, nessun ID ottenuto.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il caricamento del corso sul database", e);
        }
    }

    private void inserisciDettagliCorso(int corsoId) throws SQLException {
        String queryDettagli = "INSERT INTO dettagli_c_acatalogo (id_catalogo, settore, descrizione, argomenti, costo_persona, mod_erogazione, tipo_servizio, durata) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(queryDettagli)) {
                stmt.setInt(1, corsoId);
                stmt.setString(2, settore);
                stmt.setString(3, descrizione);
                stmt.setString(4, argomento);
                stmt.setDouble(5, costoAPersona);
                stmt.setString(6, modalita.toString());
                stmt.setString(7, tipoServizio.toString());
                stmt.setInt(8, durata);
                stmt.executeUpdate();
            }
        }
    }


    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public double getCostoAPersona() {
        return costoAPersona;
    }

    public void setCostoAPersona(double costoAPersona) {
        this.costoAPersona = costoAPersona;
    }

    public boolean isOnDatabase() {
        return onDatabase;
    }

    @Override
    public String toString() {
        return "CorsoCatalogo{" +
                "titolo='" + titolo + '\'' +
                ", settore='" + settore + '\'' +
                ", argomento='" + argomento + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", costoAPersona=" + costoAPersona +
                ", modalita=" + modalita +
                ", tipoServizio=" + tipoServizio +
                '}';
    }

    public static void stampaCorsiConUnaSolaClasse() {
        String query = """
            SELECT 
                ca.titolo, 
                dc.settore, 
                dc.argomenti, 
                dc.mod_erogazione, 
                dc.descrizione, 
                COUNT(c.codice) AS num_classi
            FROM corso_acatalogo ca
            LEFT JOIN classe c ON ca.id_C_catalogo = c.id_corso
            LEFT JOIN dettagli_c_acatalogo dc ON ca.id_C_catalogo = dc.id_catalogo
            GROUP BY ca.titolo, dc.settore, dc.argomenti, dc.mod_erogazione, dc.descrizione
            HAVING COUNT(c.codice) = 1;
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Corsi con una sola classe e relativi dettagli:");

                boolean corsiTrovati = false;

                while (rs.next()) {
                    corsiTrovati = true;

                    String titolo = rs.getString("titolo");
                    String settore = rs.getString("settore");
                    String argomenti = rs.getString("argomenti");
                    String mod_erogazione = rs.getString("mod_erogazione");
                    String descrizione = rs.getString("descrizione");
                    int numClassi = rs.getInt("num_classi");

                    System.out.printf("Titolo: %s, Settore: %s, Argomenti: %s, Modalità erogazione: %s, Descrizione: %s, Numero Classi: %d%n",
                            titolo, settore, argomenti, mod_erogazione, descrizione, numClassi);
                }

                if (!corsiTrovati) {
                    System.out.println("Nessun corso trovato con una sola classe formata.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'esecuzione della query.", e);
        }
    }

    public void stampaDettagliCorsoConDiscenti() {
        String query = """
            SELECT 
                c.*,
                d.*,
                IFNULL(SUM(i.n_dipendenti),0) AS totale_discenti
            FROM corso_acatalogo c
            JOIN dettagli_c_acatalogo d ON c.id_C_catalogo = d.id_catalogo
            LEFT JOIN classe cl ON c.id_C_catalogo = cl.id_corso
            LEFT JOIN iscrizione i ON cl.codice = i.codice_classe
            WHERE c.id_C_catalogo = ?
            GROUP BY c.id_C_catalogo, d.id_catalogo;
        """;

        try (Connection conn = Database.getConnection(); // Assicurati di avere un metodo Database.getConnection()
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Impostazione del parametro (codice della classe)
            ps.setInt(1, this.getId());

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Dettagli del corso:");

                boolean corsiTrovati = false; // Variabile per tracciare se sono stati trovati corsi

                // Itera attraverso i risultati della query
                while (rs.next()) {
                    corsiTrovati = true;

                    // Recupera i dati dal ResultSet
                    String titolo = rs.getString("titolo");
                    int totaleDiscenti = rs.getInt("totale_discenti");
                    String descrizione = rs.getString("descrizione");
                    String modErogazione = rs.getString("mod_erogazione");
                    String settore = rs.getString("settore");
                    String argomento = rs.getString("argomenti");
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
                    System.out.println("Nessun corso trovato per la classe con codice: " + this.getId());
                }
            }

        } catch (SQLException e) {
            // Gestione dell'eccezione in caso di errore durante la query
            throw new RuntimeException("Errore durante l'esecuzione della query.", e);
        }
    }

    public static void caricaTriggerVerificaTitoloCorsi() {
        String query = """
            CREATE TRIGGER verifica_titolo_corsi
            BEFORE INSERT
            ON corso_acatalogo
            FOR EACH ROW
            BEGIN
                DECLARE duplicati INT;
                SELECT COUNT(*)
                INTO duplicati
                FROM corso_acatalogo
                WHERE titolo = NEW.titolo;
                IF duplicati > 0 THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Esiste già un corso con seguente titolo, riprova';
                END IF;
            END;
        """;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Trigger caricato con successo.");
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il caricamento del trigger.", e);
        }
    }
}