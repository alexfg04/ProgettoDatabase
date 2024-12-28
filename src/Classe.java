import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Classe {
    private int codice;
    private LocalDate inizio;
    private LocalDate fine;
    private LocalDate scadenzaIscrizione;
    private double ricavo;
    private boolean onDatabase;

    public Classe(int codice, LocalDate inizio, LocalDate fine, LocalDate scadenzaIscrizione) {
        this.codice = codice;
        this.inizio = inizio;
        this.fine = fine;
        this.scadenzaIscrizione = scadenzaIscrizione;
        this.ricavo = 0;
        this.onDatabase = false;
    }

    protected void caricaSuDatabase() {
        // Codice per caricare i dati su un database
        try(Connection conn = Database.getConnection()) {
            String query = "INSERT INTO Classe (codice, data_in, data_fine, scadenza_iscrizioni) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, codice);
            stmt.setDate(2, Date.valueOf(inizio));
            stmt.setDate(3, Date.valueOf(fine));
            stmt.setDate(4, Date.valueOf(scadenzaIscrizione));
            stmt.executeUpdate();
            stmt.close();
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
        // Codice per aggiornare il ricavo su un database
        try(Connection conn = Database.getConnection()) {
            String query = "UPDATE Classe SET ricavo = ? WHERE codice = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, ricavo);
            stmt.setInt(2, codice);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
