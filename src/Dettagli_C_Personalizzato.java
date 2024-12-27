public class Dettagli_C_Personalizzato extends Corso_Personalizzato{

    private boolean DMA; // Dispositivo o strumento richiesto
    private int n_trasferte; // Numero di trasferte previste
    private String Descrizione;
    private Double Costo_servizio;

    // Enum per la modalità di erogazione
    public enum ModalitaErogazione {
        DISTANZA,
        PRESENZA,
        IBRIDA
    }

    // Enum per il tipo di servizio
    public enum TipoServizio {
        LEZIONE,
        LABORATORIO,
        SEMINARI
    }

    // Campi per memorizzare i valori degli enum
    private ModalitaErogazione modalita;
    private TipoServizio tipoServizio;

    // Costruttore
    public Dettagli_C_Personalizzato(int id_Corso_Personalizzato, String titolo, String date_in, String date_fine, String tutor,boolean DMA, int n_trasferte, String Descrizione, Double Costo_servizio, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        super(id_Corso_Personalizzato,titolo,date_in,date_fine,tutor);
        this.DMA = DMA;
        this.n_trasferte = n_trasferte;
        this.Descrizione = Descrizione;
        this.Costo_servizio = Costo_servizio;
        this.modalita = modalita;
        this.tipoServizio = tipoServizio;
    }

    // Getter e Setter
    public boolean isDMA() {
        return DMA;
    }

    public void setDMA(boolean DMA) {
        this.DMA = DMA;
    }

    public int getN_trasferte() {
        return n_trasferte;
    }

    public void setN_trasferte(int n_trasferte) {
        this.n_trasferte = n_trasferte;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String Descrizione) {
        this.Descrizione = Descrizione;
    }

    public Double getCosto_servizio() {
        return Costo_servizio;
    }

    public void setCosto_servizio(Double Costo_servizio) {
        this.Costo_servizio = Costo_servizio;
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
        return "Dettagli_C_Personalizzato{" +
                "DMA=" + DMA +
                ", n_trasferte=" + n_trasferte +
                ", Descrizione='" + Descrizione + '\'' +
                ", Costo_servizio=" + Costo_servizio +
                ", Modalita=" + modalita +
                ", TipoServizio=" + tipoServizio +
                '}';
    }
}
