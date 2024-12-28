import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Erogratrice extends Azienda {
    private double ricavo;
    public Erogratrice(String partita_iva, TipoAzienda tipo, String mission, String nome, int numeroDipendenti) {
        super(partita_iva, tipo, mission, nome, numeroDipendenti);
    }

    public double getRicavo() {
        return ricavo;
    }

    public void setRicavo(double ricavo) {
        this.ricavo = ricavo;
    }

    public void definisci(Classe classe) {
        if(!classe.isOnDatabase()) {
            classe.caricaSuDatabase();
        }
        // Codice per definire la classe su un database
        try(Connection conn = Database.getConnection()) {
            String query = "INSERT INTO Definizione (id_azienda, codice_classe)" +
                    "VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, this.getPartitaIva());
            stmt.setInt(2, classe.getCodice());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
