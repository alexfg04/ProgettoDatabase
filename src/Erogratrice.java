import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Erogratrice extends Azienda {
    private double ricavo;
    public Erogratrice(String partitaIva, TipoAzienda tipo, String mission, String nome, int numeroDipendenti) {
        super(partitaIva, tipo, mission, nome, numeroDipendenti);
        ricavo = 0.0;
    }

    public double getRicavo() {
        return ricavo;
    }

    public void setRicavo(double ricavo) {
        this.ricavo = ricavo;
    }

    public void AggiungiDocenteAClasse(int codiceClasse, String codiceFiscaleDocente) {
        try(Connection conn = Database.getConnection()) {
            // Query SQL per inserire un record nella tabella Collaborazione
            String query = "INSERT INTO Collaborazione (id_classe, persona) VALUES (?, ?)";

            // Creazione di un PreparedStatement per eseguire la query
            PreparedStatement ps = conn.prepareStatement(query);

            // Impostazione dei parametri della query
            ps.setInt(1, codiceClasse);             // Imposta il codice della classe (id_classe)
            ps.setString(2, codiceFiscaleDocente);  // Imposta il codice fiscale del docente (persona)

            // Esegui la query per aggiornare il database
            ps.executeUpdate();

            // Messaggio di successo
            System.out.println("Tutor aggiunto alla classe");

        } catch(SQLException e) {
            // Gestione degli errori di SQL
            throw new RuntimeException(e);
        }
    }

    public void ErogaCorso(CorsoCatalogo corso) {
        if(!corso.isOnDatabase()) {
            corso.caricaSuDatabase(getPartitaIva());
        }
    }


    /*  Modifica del Docente/Tutor assegnato con al corso Personalizzato*/
    public void ModificaDocenteCorsoPersonalizzato(int idCorsoPersonalizzato, String nuovoCodiceFiscaleTutor) {
        String query = "UPDATE Corso_Personalizzato SET tutor = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            // Imposta i parametri della query
            ps.setString(1, nuovoCodiceFiscaleTutor);
            ps.setInt(2, idCorsoPersonalizzato);
            // Esegui l'aggiornamento
             ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la modifica del tutor per il corso personalizzato.", e);
        }
    }

    public void verificaTutorDisponibili() {
        boolean tutorNonCoinvolti = false;
        String query = "SELECT T.cf, T.nome, T.cognome FROM docenti_tutor T LEFT JOIN corso_personalizzato C ON T.cf = C.tutor WHERE C.tutor IS NULL; ";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Tutor non coinvolti in corsi:");

            while (rs.next()) {
                tutorNonCoinvolti = true;
                String codiceFiscale = rs.getString("cf");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                System.out.printf("Codice Fiscale: %s - Nome: %s - Cognome: %s%n", codiceFiscale, nome, cognome);
            }

            if (!tutorNonCoinvolti) {
                System.out.println("Tutti i tutor sono attualmente coinvolti in corsi.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la verifica dei tutor non coinvolti in corsi.", e);
        }
    }

    // Verifica se il docente/tutor non è associato a nessun corso personalizzato
    public boolean verificaTutor(String codiceFiscaleDocente) {
        String query = "SELECT * FROM docenti_tutor WHERE cf = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codiceFiscaleDocente);
            try (ResultSet rs = ps.executeQuery()) {
                // next restituisce true se il ResultSet contiene almeno una riga
                return !rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la verifica del docente.", e);
        }
    }


    // Stampa di tutti i corsi a catalogo messi a disposizione da un’azienda erogatrice //
    public void stampaCorsiCatalogo() {
        String query = """
        SELECT 
            CC.titolo, 
            DC.settore, 
            DC.argomenti, 
            DC.descrizione, 
            DC.costo_persona 
        FROM corso_acatalogo CC 
        JOIN dettagli_c_acatalogo DC ON CC.id_C_catalogo = DC.id_catalogo 
        JOIN azienda A ON CC.id_azienda = A.p_iva 
        WHERE A.p_iva = ?
    """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            // Imposta il parametro della query
            ps.setString(1, this.getPartitaIva());

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Corsi a catalogo offerti dall'azienda:");

                boolean corsiTrovati = false;
                while (rs.next()) {
                    corsiTrovati = true;

                    String titolo = rs.getString("titolo");
                    String settore = rs.getString("settore");
                    String argomento = rs.getString("argomento");
                    String descrizione = rs.getString("descrizione");
                    double costoAPersona = rs.getDouble("costo_a_persona");

                    System.out.printf(
                            "Titolo: %s, Settore: %s, Argomento: %s, Descrizione: %s, Costo a persona: %.2f%n",
                            titolo, settore, argomento, descrizione, costoAPersona
                    );
                }

                if (!corsiTrovati) {
                    System.out.println("Nessun corso a catalogo trovato per questa azienda.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la stampa dei corsi a catalogo.", e);
        }
    }

        // Metodo per stampare le aziende non impegnate in corsi personalizzati
        public void stampaAziendeNonImpegnateInCorsiPersonalizzati() {
            // La query SQL per selezionare le aziende non impegnate in corsi personalizzati
            String query = """
            SELECT a.p_iva, a.tipo, a.mission, a.denominazione
            FROM azienda a
            LEFT JOIN impiego i ON a.p_iva = i.id_azienda
            LEFT JOIN docenti_tutor dt ON i.dipendente = dt.cf
            LEFT JOIN corso_personalizzato cp ON dt.cf = cp.tutor
            JOIN ruolo r ON a.p_iva = r.id_azienda
            WHERE r.nome_ruolo = 'erogatrice' AND cp.tutor IS NULL;
        """;

            // Connessione al database e preparazione della query
            try (Connection conn = Database.getConnection(); // Assicurati di avere un metodo Database.getConnection()
                 PreparedStatement ps = conn.prepareStatement(query)) {

                // Esecuzione della query
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("Aziende non impegnate in corsi personalizzati:");

                    boolean aziendeTrovate = false; // Variabile per tracciare se sono state trovate aziende

                    // Scorri i risultati della query
                    while (rs.next()) {
                        aziendeTrovate = true;

                        // Recupera i dati delle colonne dalla query
                        String pIva = rs.getString("p_iva");
                        String tipoAzienda = rs.getString("tipo_azienda");
                        String mission = rs.getString("mission");
                        String denominazione = rs.getString("denominazione");

                        // Stampa i dettagli dell'azienda
                        System.out.printf(
                                "P. IVA: %s, Tipo: %s, Mission: %s, Denominazione: %s%n",
                                pIva, tipoAzienda, mission, denominazione
                        );
                    }

                    // Se non sono state trovate aziende, stampa un messaggio
                    if (!aziendeTrovate) {
                        System.out.println("Nessuna azienda erogatrice non impegnata in corsi personalizzati.");
                    }
                }

            } catch (SQLException e) {
                // Gestione dell'eccezione in caso di errore durante la query
                throw new RuntimeException("Errore durante l'esecuzione della query.", e);
            }
        }
    }


