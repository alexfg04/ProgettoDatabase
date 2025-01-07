import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CorsoCatalogo extends Corso {
    private String settore;
    private String argomento;
    private double costoAPersona;

    private boolean onDatabase;

    public CorsoCatalogo(String titolo, String settore, String descrizione, String argomento, double costoAPersona, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        super(titolo, descrizione, modalita, tipoServizio);
        this.settore = settore;
        this.argomento = argomento;
        this.costoAPersona = costoAPersona;
        this.onDatabase = false;
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
        String queryDettagli = "INSERT INTO dettagli_c_acatalogo (id_catalogo, settore, descrizione, argomenti, costo_persona, mod_erogazione, tipo_servizio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection()) {
            try (PreparedStatement stmtDettagli = conn.prepareStatement(queryDettagli)) {
                stmtDettagli.setInt(1, corsoId);
                stmtDettagli.setString(2, settore);
                stmtDettagli.setString(3, descrizione);
                stmtDettagli.setString(4, argomento);
                stmtDettagli.setDouble(5, costoAPersona);
                stmtDettagli.setString(6, modalita.toString());
                stmtDettagli.setString(7, tipoServizio.toString());
                stmtDettagli.executeUpdate();
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

    public void stampaCorsiConUnaSolaClasse() {
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
                    String mod_erogazione = rs.getString("modalita erogazione");
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
}