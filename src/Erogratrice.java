import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Erogratrice extends Azienda {
    private double ricavo;
    public Erogratrice(String partitaIva, TipoAzienda tipo, String mission, String nome, int numeroDipendenti) {
        super(partitaIva, tipo, mission, nome, numeroDipendenti);
        ricavo = 0.0;
    }

    public double getRicavo() {
        return ricavo;
    }

    public void setRicavo(double ricavo) {
        this.ricavo = ricavo;
    }

    public void AggiungiDocenteAClasse(int codiceClasse, String codiceFiscaleDocente) {
        String query = "INSERT INTO Collaborazione (id_classe, persona)" +
                "VALUES (?, ?)";
        try(Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, codiceClasse);
            ps.setString(2, codiceFiscaleDocente);

            ps.executeUpdate();
            System.out.println("Tutor aggiunto alla classe");

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ErogaCorso(CorsoCatalogo corso) {
        if(!corso.isOnDatabase()) {
            corso.caricaSuDatabase(getPartitaIva());
        }
    }

    /*  Modifica del Docente/Tutor assegnato con al corso  */
    public void ModificaDocenteCorsoPersonalizzato(int idCorsoPersonalizzato, String nuovoCodiceFiscaleTutor) {
        String query = "UPDATE Corso_Personalizzato SET tutor = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            // Imposta i parametri della query
            ps.setString(1, nuovoCodiceFiscaleTutor);
            ps.setInt(2, idCorsoPersonalizzato);
            // Esegui l'aggiornamento
             ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la modifica del tutor per il corso personalizzato.", e);
        }
    }

    /* Stampa i tutor che non sono impegnati in nessun corso */
    public void verificaTutorDisponibili() {
        boolean tutorNonCoinvolti = false;
        String query = "SELECT T.cf, T.nome, T.cognome FROM docenti_tutor T " +
                "LEFT JOIN corso_personalizzato C ON T.cf = C.tutor WHERE C.tutor IS NULL; ";

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

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

    /* Verifica se il docente/tutor non è associato a nessun corso personalizzato */
    public boolean verificaTutor(String codiceFiscaleDocente) {
        String query = "SELECT * FROM docenti_tutor WHERE cf = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codiceFiscaleDocente);
            ResultSet rs = ps.executeQuery();
            // next restituisce true se il ResultSet contiene almeno una riga
            return !rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la verifica del docente.", e);
        }
    }
}
