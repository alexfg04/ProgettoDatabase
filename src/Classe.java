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

    private double calcolaRicavo() {
        String query = """
        SELECT 
            dc.costo_persona * (SELECT SUM(I.n_dipendenti)
                                FROM iscrizione I 
                                WHERE I.codice_classe = C.codice) AS ricavo_classe
        FROM Classe C
        JOIN corso_acatalogo ca ON C.id_corso = ca.id_c_catalogo
        JOIN dettagli_c_acatalogo dc ON ca.id_c_catalogo = dc.id_catalogo
        WHERE C.codice = ?;
    """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, codice);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("ricavo_classe");
                } else {
                    throw new RuntimeException("Classe non trovata per il codice: " + codice);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'esecuzione della query.", e);
        }
    }

    protected void caricaSuDatabase(String Azienda, int idCorso) {
        if(isOnDatabase()) {
            return;
        }
        String query = "INSERT INTO Classe (codice, azienda, data_in, data_fine, scadenza_iscrizioni, id_corso) VALUES (?, ?, ?, ?, ?, ?)";
        // Codice per caricare i dati su un database
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, codice);
            stmt.setString(2, Azienda);
            stmt.setDate(3, Date.valueOf(inizio));
            stmt.setDate(4, Date.valueOf(fine));
            stmt.setDate(5, Date.valueOf(scadenzaIscrizione));
            stmt.setInt(6, idCorso);
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

    public void setRicavo() {
        this.ricavo = calcolaRicavo();
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

    public static void stampaClasse() {
        String query = "SELECT * FROM Classe";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
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


    public static void stampaTutteLeClassi() {
        String query = """
            SELECT
                C.codice,
                C.data_in AS data_inizio,
                C.data_fine,
                C.scadenza_iscrizioni,
                A.denominazione,
                dc.costo_persona * (SELECT SUM(I.n_dipendenti)
                                    FROM iscrizione I 
                                    WHERE I.codice_classe = C.codice) AS ricavo
            FROM Classe C
            JOIN corso_acatalogo ca ON C.id_corso = ca.id_c_catalogo
            JOIN dettagli_c_acatalogo dc ON ca.id_c_catalogo = dc.id_catalogo
            JOIN Azienda A ON C.azienda = A.p_iva
            ORDER BY C.codice, A.denominazione;
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int codice = rs.getInt("codice");
                Date dataInizio = rs.getDate("data_inizio");
                Date dataFine = rs.getDate("data_fine");
                Date scadenzaIscrizioni = rs.getDate("scadenza_iscrizioni");
                String denominazione = rs.getString("denominazione");
                double ricavo = rs.getDouble("ricavo");

                System.out.printf("Codice: %d%nData Inizio: %s%nData Fine: %s%nScadenza Iscrizioni: %s%nDenominazione: %s%nRicavo: %.2f%n",
                        codice, dataInizio, dataFine, scadenzaIscrizioni, denominazione, ricavo);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'esecuzione della query.", e);
        }
    }
}
