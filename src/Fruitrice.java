import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Fruitrice extends Azienda {
    public Fruitrice(String partita_iva, TipoAzienda tipo, String mission, String nome, int numeroDipendenti) {
        super(partita_iva, tipo, mission, nome, numeroDipendenti);
    }

    public void iscriviAClasse(int codiceClasse, int numeroDipendenti) {
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

    public void richiediPersonalizzato(int idCorso) {
        String query = "INSERT INTO Richiesta (id_azienda, id_c_pers, data_richiesta" +
                "VALUES (?, ?, ?)";
        try(Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, this.getPartitaIva());
            ps.setInt(2, idCorso);
            ps.setDate(3, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();
            System.out.println("Richiesta inviata");

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
