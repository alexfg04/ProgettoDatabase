import java.sql.*;
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
        String query = "INSERT INTO Richiesta (id_azienda, id_c_pers, data_richiesta)" +
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


    public static void stampaClassificaAziende() {
        // Query SQL
        String query = """
        SELECT 
            a.p_iva AS azienda_piva,
            a.denominazione AS azienda_nome,
            (IFNULL(num_classi, 0) + IFNULL(num_corsi_pers, 0)) AS totale_servizi
        FROM azienda a
        LEFT JOIN (
            SELECT 
                i.id_azienda,
                COUNT(i.codice_classe) AS num_classi
            FROM iscrizione i
            GROUP BY i.id_azienda
        ) classi ON a.p_iva = classi.id_azienda
        LEFT JOIN (
            SELECT 
                r.id_azienda,
                COUNT(r.id_c_pers) AS num_corsi_pers
            FROM richiesta r
            GROUP BY r.id_azienda
        ) corsi_personalizzati ON a.p_iva = corsi_personalizzati.id_azienda
        ORDER BY totale_servizi DESC;
    """;

        // Connessione al database
        try (Connection conn = Database.getConnection(); // Assicurati di avere un metodo Database.getConnection()
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Esecuzione della query
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Classifica delle aziende in base al numero di servizi  richiesti:");

                // Scorri i risultati della query
                while (rs.next()) {
                    String aziendaPiva = rs.getString("azienda_piva");
                    String aziendaNome = rs.getString("azienda_nome");
                    int totaleServizi = rs.getInt("totale_servizi");

                    // Stampa i dettagli dell'azienda
                    System.out.printf("P. IVA: %s, Nome: %s, Totale Servizi: %d%n",
                            aziendaPiva, aziendaNome, totaleServizi);
                }
            }

        } catch (SQLException e) {
            // Gestione dell'eccezione in caso di errore durante la query
            throw new RuntimeException("Errore durante l'esecuzione della query.", e);
        }
    }

    public static void caricaTriggerVerificaLimiteSpesa() {
        String query = """
        CREATE TRIGGER verifica_limite_spesa
        BEFORE INSERT
        ON richiesta
        FOR EACH ROW
        BEGIN
            DECLARE totale_spesa DECIMAL(10, 2);
            -- Calculate the total spending for the company so far
            SELECT COALESCE(SUM(DC.costo), 0)
            INTO totale_spesa
            FROM richiesta R
            JOIN corso_personalizzato C ON R.id_c_pers = C.id
            JOIN dettagli_corso_personalizzato DC ON C.id = DC.id_corso
            WHERE R.id_azienda = NEW.id_azienda;
            -- Add the cost of the new course being inserted
            SET totale_spesa = totale_spesa + (
                SELECT COALESCE(SUM(DC.costo), 0)
                FROM corso_personalizzato C
                JOIN dettagli_corso_personalizzato DC ON C.id = DC.id_corso
                WHERE C.id = NEW.id_c_pers
            );

            -- Check if the spending limit is exceeded
            IF totale_spesa > 40000 THEN
                SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'azienda non può spendere più di 40.000 euro per corsi personalizzati';
            END IF;
        END
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
