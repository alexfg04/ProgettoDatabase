public class Dettagli_C_Catalogo extends Corso_Catalogo {

    private String Settore;
    private String Argomento;
    private String Descrizione;
    private double Costo_A_Persona;

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
    public Dettagli_C_Catalogo(int id_C_Cata, String Titolo, String Settore, String Argomento,String Descrizione, double Costo_A_Persona,ModalitaErogazione modalita, TipoServizio tipoServizio) {
        super(id_C_Cata, Titolo);
        this.Settore = Settore;
        this.Argomento = Argomento;
        this.Descrizione = Descrizione;
        this.Costo_A_Persona = Costo_A_Persona;
        this.modalita = modalita;
        this.tipoServizio = tipoServizio;
    }


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
        return "Dettagli_C_Catalogo{" +
                "Settore='" + Settore + '\'' +
                ", Argomento='" + Argomento + '\'' +
                ", Descrizione='" + Descrizione + '\'' +
                ", Costo_A_Persona=" + Costo_A_Persona +
                ", Modalita=" + modalita +
                ", TipoServizio=" + tipoServizio +
                '}';
    }
}
