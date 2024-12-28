import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
