public abstract class Corso {
    private static int currentId = 0;
    private int id;
    protected String titolo;
    protected String descrizione;
    protected ModalitaErogazione modalita;
    protected TipoServizio tipoServizio;

    public Corso(String titolo, String descrizione, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.modalita = modalita;
        this.tipoServizio = tipoServizio;
        id = ++currentId;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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
}
