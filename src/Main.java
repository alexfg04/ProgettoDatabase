import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Erogatrice erogatrice = new Erogatrice("23456789012", TipoAzienda.PRIVATA, "Innovazione software", "Azienda Beta", 80);
        Erogatrice azienda2 = new Erogatrice("56789012345", TipoAzienda.PUBBLICA, "Corsi di formazione aziendale", "Azienda Epsilon", 100);
        Erogatrice azienda3 = new Erogatrice("45678901234", TipoAzienda.PUBBLICA, "Ricerca scientifica avanzata", "Azienda Delta", 50);
        Fruitrice fruitrice = new Fruitrice("34567890123", TipoAzienda.PUBBLICA, "Sviluppo applicazioni cloud", "Azienda Gamma", 200);
        CorsoCatalogo corso1 = new CorsoCatalogo(
                "Catalogo Data Science",
                "IT",
                "Corso introduttivo sui dati",
                "Dati, Python, R",
                300.00,
                ModalitaErogazione.DISTANZA,
                TipoServizio.LABORATORIO,
                30
        );

        CorsoCatalogo corso2 = new CorsoCatalogo(
                "Catalogo Machine Learning",
                "IT",
                "Tecniche avanzate ML",
                "ML, Algoritmi, Python",
                500.00,
                ModalitaErogazione.IBRIDA,
                TipoServizio.LEZIONE,
                40
        );

        //erogatrice.ErogaCorso(corso2);
        //erogatrice.ErogaCorso(corso1);

        Classe classe1 = new Classe(101, LocalDate.of(2024, 1, 10), LocalDate.of(2024, 3, 10), LocalDate.of(2024, 1, 1));
        Classe classe2 = new Classe(102, LocalDate.of(2024, 2, 15), LocalDate.of(2024, 4, 15), LocalDate.of(2024, 2, 1));
        Classe classe3 = new Classe(103, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 5, 1), LocalDate.of(2024, 2, 20));
        Classe classe4 = new Classe(104, LocalDate.of(2024, 1, 20), LocalDate.of(2024, 3, 20), LocalDate.of(2024, 1, 10));
        Classe classe5 = new Classe(105, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 6, 1), LocalDate.of(2024, 3, 25));

        Fruitrice.caricaTriggerVerificaLimiteSpesa();


    }
}