import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Fruitrice extends Azienda {
    public Fruitrice(String partita_iva, TipoAzienda tipo, String mission, String nome, int numeroDipendenti) {
        super(partita_iva, tipo, mission, nome, numeroDipendenti);
    }

    public void iscrivi(int codiceClasse, int numeroDipendenti) {
        String query = "INSERT INTO Iscrizione (id_azienda, codice_classe, n_dipendenti)" +
                "VALUES (?, ?, ?)";

        try(Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, this.getPartitaIva());
            ps.setInt(2, codiceClasse);
            ps.setInt(3, numeroDipendenti);

            ps.executeUpdate();
            System.out.println("Iscrizione avvenuta");

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
