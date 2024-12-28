public class CorsoCatalogo {
    private static int id;
    private String titolo;

    public CorsoCatalogo(String titolo, String settore, String descrizione, String argomento, double costo_A_Persona, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        this.titolo = titolo;
        this.settore = settore;
        this.descrizione = descrizione;
        this.argomento = argomento;
        costoAPersona = costo_A_Persona;
        this.modalita = modalita;
        this.tipoServizio = tipoServizio;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    private String settore;
    private String argomento;
    private String descrizione;
    private double costoAPersona;

    // Campi per memorizzare i valori degli enum
    private ModalitaErogazione modalita;
    private TipoServizio tipoServizio;

    // Getter e Setter
    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getCostoAPersona() {
        return costoAPersona;
    }

    public void setCostoAPersona(double costoAPersona) {
        this.costoAPersona = costoAPersona;
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

    // Metodo per stampare i dettagli

    @Override
    public String toString() {
        return "Corso_Catalogo{" +
                "Titolo='" + titolo + '\'' +
                ", Settore='" + settore + '\'' +
                ", Argomento='" + argomento + '\'' +
                ", Descrizione='" + descrizione + '\'' +
                ", Costo_A_Persona=" + costoAPersona +
                ", modalita=" + modalita +
                ", tipoServizio=" + tipoServizio +
                '}';
    }
}
