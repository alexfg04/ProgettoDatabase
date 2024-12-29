# Progetto Basi di Dati

Progetto per il corso di Basi di Dati, anno accademico 2024/2025.
Parte 3 del progetto.

## Modifiche effettuate
### Modifiche al database:
- Rimossa la tabella Gestione e aggiunto il campo tutor come chiave esterna in Corso_Personalizzato
- Rimosso la tabella Definizione e aggiunto il campo azienda come chiave esterna in Classe

### Modifiche alle parti precedenti del progetto:
- Cambiata la cardinalità tra Proposta e A Catalogo da 1:1 a 1:N (nello schema relazionale rimane invariato).
- Cardinalità tra Gestione e Personalizzato cambiata da 1:N a 1:1.

## Tasks
- [ ] Registrazione di un corso a catalogo;
- [x] Iscrizione di un'azienda a una classe;
- [x] Richiesta di un corso personalizzato;
- [x] Aggiunta di un nuovo docente/tutor a una classe;
- [x] Modifica del docente/tutor a cui è affidato un corso personalizzato;
- [ ] Stampa di tutti i corsi a catalogo messi a disposizione da un’azienda erogatrice;
- [ ] Stampa di tutte le aziende erogatrici non impegnate in corsi personalizzati;
- [ ] Verifica della possibilità di assegnare un tutor/docente ad un corso;
- [ ] Verifica l’eventuale presenza di tutor attualmente non coinvolti in corsi;
- [ ] Per ciascun corso a catalogo, stampare il numero totale di discenti;
- [ ] Stampa i dati del docente/tutor maggiormente impiegato in corsi (a catalogo e/o personalizzato);
- [ ] Stampa di tutti i corsi a catalogo per i quali non si è mai formata più di una classe;
- [ ] Stampa dei dati delle aziende erogatrici, compreso il ricavo totale che hanno ottenuto dall’erogazione di tutte le tipologie di corsi;
- [ ] Stampa di ogni classe, compreso il ricavo ottenuto mediante la definizione della stessa;
- [ ] Stampa una classifica delle aziende fruitrici sulla base del numero di servizi che ha richiesto (e.g., corsi di formazione personalizzati e/o corsi a catalogo).