public class Corso_Personalizzato {

    private static int id_Corso_Personalizzato;
    private String titolo;
    private String date_in;
    private String date_fine;
    private String tutor;

    // Costruttore
    public Corso_Personalizzato(int id_Corso_Personalizzato, String titolo, String date_in, String date_fine, String tutor) {
        this.id_Corso_Personalizzato++;
        this.titolo = titolo;
        this.date_in = date_in;
        this.date_fine = date_fine;
        this.tutor = tutor;
    }

    // Getter e Setter
    public int getId() {
        return id_Corso_Personalizzato;
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

    // Metodo toString
    @Override
    public String toString() {
        return "Corso_Personalizzato{" +
                "id=" + id_Corso_Personalizzato +
                ", titolo='" + titolo + '\'' +
                ", date_in='" + date_in + '\'' +
                ", date_fine='" + date_fine + '\'' +
                ", tutor='" + tutor + '\'' +
                '}';
    }
}
