import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class CorsoPersonalizzato extends Corso {
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Tutor tutor;
    private boolean DMA;
    private int numTrasferte;
    private Double costoServizio;

    public CorsoPersonalizzato(String titolo, LocalDate dataInizio, LocalDate dataFine, Tutor tutor, boolean DMA, int numTrasferte, String descrizione, Double costoServizio, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        super(titolo, descrizione, modalita, tipoServizio);
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tutor = tutor;
        this.DMA = DMA;
        this.numTrasferte = numTrasferte;
        this.costoServizio = costoServizio;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public boolean isDMA() {
        return DMA;
    }

    public void setDMA(boolean DMA) {
        this.DMA = DMA;
    }

    public int getNumTrasferte() {
        return numTrasferte;
    }

    public void setNumTrasferte(int numTrasferte) {
        this.numTrasferte = numTrasferte;
    }

    public Double getCostoServizio() {
        return costoServizio;
    }

    public void setCostoServizio(Double costoServizio) {
        this.costoServizio = costoServizio;
    }

    public static void caricaTriggerVerificaLimiteCorsi() {
        String query = """
            CREATE TRIGGER verifica_limite_corsi
            BEFORE INSERT
            ON corso_personalizzato
            FOR EACH ROW
            BEGIN
                DECLARE num_corsi INT;
                SELECT COUNT(*)
                INTO num_corsi
                FROM corso_personalizzato
                WHERE tutor = NEW.tutor;
                IF num_corsi >= 3 THEN
                    SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Il tutor non può gestire più di 3 corsi';
                END IF;
            END;
        """;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Trigger caricato con successo.");
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il caricamento del trigger.", e);
        }
    }

    @Override
    public String toString() {
        return "CorsoPersonalizzato{" +
                "titolo='" + titolo + '\'' +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tutor='" + tutor + '\'' +
                ", DMA=" + DMA +
                ", numTrasferte=" + numTrasferte +
                ", descrizione='" + descrizione + '\'' +
                ", costoServizio=" + costoServizio +
                ", modalita=" + modalita +
                ", tipoServizio=" + tipoServizio +
                '}';
    }
}