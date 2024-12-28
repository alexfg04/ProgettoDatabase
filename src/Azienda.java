public abstract class Azienda {
    private String partitaIva;
    private TipoAzienda tipo;
    private String mission;
    private String nome;
    private int numeroDipendenti;

    public Azienda(String partitaIva, TipoAzienda tipo, String mission, String nome, int numeroDipendenti) {
        this.partitaIva = partitaIva;
        this.tipo = tipo;
        this.mission = mission;
        this.nome = nome;
        this.numeroDipendenti = numeroDipendenti;
    }

    public String getPartitaIva() {
        return partitaIva;
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
