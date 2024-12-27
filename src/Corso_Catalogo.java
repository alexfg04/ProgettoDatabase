public class Corso_Catalogo {


    private static int id_C_Cata;
    private String Titolo;


    public Corso_Catalogo(int id_C_Cata, String titolo) {

        this.id_C_Cata++;
        this.Titolo = titolo;

    }
    public int getId_C_Cata() {
        return id_C_Cata;
    }

    public void setId_C_Cata(int id_C_Cata) {
        this.id_C_Cata = id_C_Cata;
    }


    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }
}
