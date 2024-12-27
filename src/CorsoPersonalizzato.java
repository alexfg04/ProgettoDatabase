public class CorsoPersonalizzato {

    private static int id_cat;
    private String titolo;

    private String date_in;
    private String date_fine;
    private String tutor;
    private boolean DMA; // Dispositivo o strumento richiesto
    private int n_trasferte; // Numero di trasferte previste
    private String Descrizione;
    private Double Costo_servizio;

    // Campi per memorizzare i valori degli enum
    private ModalitaErogazione modalita;
    private TipoServizio tipoServizio;

    // Costruttore
    public CorsoPersonalizzato(String titolo, String date_in, String date_fine, String tutor, boolean DMA, int n_trasferte, String descrizione, Double costo_servizio, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        this.titolo = titolo;
        this.date_in = date_in;
        this.date_fine = date_fine;
        this.tutor = tutor;
        this.DMA = DMA;
        this.n_trasferte = n_trasferte;
        Descrizione = descrizione;
        Costo_servizio = costo_servizio;
        this.modalita = modalita;
        this.tipoServizio = tipoServizio;
    }

    // Getter e Setter
    public int getId() {
        return id_cat;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
    }

    public String getDate_fine() {
        return date_fine;
    }

    public void setDate_fine(String date_fine) {
        this.date_fine = date_fine;
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
        return "Corso_Personalizzato{" +
                "titolo='" + titolo + '\'' +
                ", date_in='" + date_in + '\'' +
                ", date_fine='" + date_fine + '\'' +
                ", tutor='" + tutor + '\'' +
                ", DMA=" + DMA +
                ", n_trasferte=" + n_trasferte +
                ", Descrizione='" + Descrizione + '\'' +
                ", Costo_servizio=" + Costo_servizio +
                ", modalita=" + modalita +
                ", tipoServizio=" + tipoServizio +
                '}';
    }
}
