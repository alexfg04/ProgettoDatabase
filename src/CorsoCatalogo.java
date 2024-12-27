public class CorsoCatalogo {
    private static int id_cat;
    private String Titolo;

    public CorsoCatalogo(String titolo, String settore, String descrizione, String argomento, double costo_A_Persona, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        Titolo = titolo;
        Settore = settore;
        Descrizione = descrizione;
        Argomento = argomento;
        Costo_A_Persona = costo_A_Persona;
        this.modalita = modalita;
        this.tipoServizio = tipoServizio;
    }

    public int getId() {
        return id_cat;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    private String Settore;
    private String Argomento;
    private String Descrizione;
    private double Costo_A_Persona;

    // Campi per memorizzare i valori degli enum
    private ModalitaErogazione modalita;
    private TipoServizio tipoServizio;

    // Getter e Setter
    public String getSettore() {
        return Settore;
    }

    public void setSettore(String settore) {
        Settore = settore;
    }

    public String getArgomento() {
        return Argomento;
    }

    public void setArgomento(String argomento) {
        Argomento = argomento;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public double getCosto_A_Persona() {
        return Costo_A_Persona;
    }

    public void setCosto_A_Persona(double costo_A_Persona) {
        Costo_A_Persona = costo_A_Persona;
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
                "Titolo='" + Titolo + '\'' +
                ", Settore='" + Settore + '\'' +
                ", Argomento='" + Argomento + '\'' +
                ", Descrizione='" + Descrizione + '\'' +
                ", Costo_A_Persona=" + Costo_A_Persona +
                ", modalita=" + modalita +
                ", tipoServizio=" + tipoServizio +
                '}';
    }
}
