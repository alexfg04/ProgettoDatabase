import java.time.LocalDate;

public class CorsoPersonalizzato {
    private static int id;
    private String titolo;

    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String tutor;
    private boolean DMA; // Dispositivo o strumento richiesto
    private int numTrasferte; // Numero di trasferte previste
    private String Descrizione;
    private Double costoServizio;

    // Campi per memorizzare i valori degli enum
    private ModalitaErogazione modalita;
    private TipoServizio tipoServizio;

    // Costruttore
    public CorsoPersonalizzato(String titolo, LocalDate DataInizio, LocalDate dataFine, String tutor, boolean DMA, int numTrasferte, String descrizione, Double costoServizio, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        this.titolo = titolo;
        this.dataInizio = DataInizio;
        this.dataFine = dataFine;
        this.tutor = tutor;
        this.DMA = DMA;
        this.numTrasferte = numTrasferte;
        Descrizione = descrizione;
        this.costoServizio = costoServizio;
        this.modalita = modalita;
        this.tipoServizio = tipoServizio;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
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

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
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

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String Descrizione) {
        this.Descrizione = Descrizione;
    }

    public Double getCostoServizio() {
        return costoServizio;
    }

    public void setCostoServizio(Double Costo_servizio) {
        this.costoServizio = Costo_servizio;
    }

    public ModalitaErogazione getModalita() {
        return modalita;
    }

    public void setModalita(ModalitaErogazione modalita) {
        this.modalita = modalita;
    }

    public TipoServizio getTipoServizio() {
        return tipoServizio;
    }

    public void setTipoServizio(TipoServizio tipoServizio) {
        this.tipoServizio = tipoServizio;
    }

    // Metodo toString
    @Override
    public String toString() {
        return "Corso_Personalizzato{" +
                "titolo='" + titolo + '\'' +
                ", date_in='" + dataInizio + '\'' +
                ", date_fine='" + dataFine + '\'' +
                ", tutor='" + tutor + '\'' +
                ", DMA=" + DMA +
                ", n_trasferte=" + numTrasferte +
                ", Descrizione='" + Descrizione + '\'' +
                ", Costo_servizio=" + costoServizio +
                ", modalita=" + modalita +
                ", tipoServizio=" + tipoServizio +
                '}';
    }
}
