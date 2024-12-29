import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javax.swing.text.html.HTML.Tag.SELECT;

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
        try(Connection conn = Database.getConnection()) {
            String query = "INSERT INTO Collaborazione (id_classe, persona)" +
                    "VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, codiceClasse);
            ps.setString(2, codiceFiscaleDocente);

            ps.executeUpdate();
            System.out.println("Tutor aggiunto alla classe");

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* Da rifare
    public void definisciClasse(@NotNull Classe classe) {
        if(!classe.isOnDatabase()) {
            classe.caricaSuDatabase(this.getPartitaIva());
        }
    }
    */

    /*  Modifica del Docente/Tutor assegnato con al corso Personaalizzato*/
    public void ModificaDocenteCorsoPersonalizzato(int idCorsoPersonalizzato, String nuovoCodiceFiscaleTutor) {
        try (Connection conn = Database.getConnection()) {
            // Query SQL per aggiornare il tutor associato al corso personalizzato
            String query = "UPDATE CorsoPersonalizzato SET tutor = ? WHERE id_corso_personalizzato = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            // Imposta i parametri della query
            ps.setString(1, nuovoCodiceFiscaleTutor);
            ps.setInt(2, idCorsoPersonalizzato);
            // Esegui l'aggiornamento
             ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la modifica del tutor per il corso personalizzato.", e);
        }
    }

    public void verificaTutorNonCoinvolti() {
        String query = """SELECT T.codice_fiscale,T.nome,T.cognome FROM Tutor T LEFT JOIN Collaborazione C ON T.codice_fiscale = C.persona WHERE C.id_classe IS NULL; """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Tutor non coinvolti in corsi:");
            boolean tutorNonCoinvolti = false;

            while (rs.next()) {
                tutorNonCoinvolti = true;
                String codiceFiscale = rs.getString("codice_fiscale");
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
