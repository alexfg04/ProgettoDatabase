public abstract class Azienda {
    private String partita_iva;
    private TipoAzienda tipo;
    private String mission;
    private String nome;
    private int numeroDipendenti;

    public Azienda(String partita_iva, TipoAzienda tipo, String mission, String nome, int numeroDipendenti) {
        this.partita_iva = partita_iva;
        this.tipo = tipo;
        this.mission = mission;
        this.nome = nome;
        this.numeroDipendenti = numeroDipendenti;
    }

    public String getPartita_iva() {
        return partita_iva;
    }

    public TipoAzienda getTipo() {
        return tipo;
    }

    public String getMission() {
        return mission;
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroDipendenti() {
        return numeroDipendenti;
    }

    public void setNumeroDipendenti(int numeroDipendenti) {
        this.numeroDipendenti = numeroDipendenti;
    }
}
