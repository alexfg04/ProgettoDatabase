public class CorsoCatalogo extends Corso {
    private String settore;
    private String argomento;
    private double costoAPersona;

    public CorsoCatalogo(String titolo, String settore, String descrizione, String argomento, double costoAPersona, ModalitaErogazione modalita, TipoServizio tipoServizio) {
        super(titolo, descrizione, modalita, tipoServizio);
        this.settore = settore;
        this.argomento = argomento;
        this.costoAPersona = costoAPersona;
    }

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

    public double getCostoAPersona() {
        return costoAPersona;
    }

    public void setCostoAPersona(double costoAPersona) {
        this.costoAPersona = costoAPersona;
    }

    @Override
    public String toString() {
        return "CorsoCatalogo{" +
                "titolo='" + titolo + '\'' +
                ", settore='" + settore + '\'' +
                ", argomento='" + argomento + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", costoAPersona=" + costoAPersona +
                ", modalita=" + modalita +
                ", tipoServizio=" + tipoServizio +
                '}';
    }
}