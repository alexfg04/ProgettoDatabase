# Progetto Basi di Dati

Progetto per il corso di Basi di Dati, anno accademico 2024/2025.
Parte 3 del progetto.

## Modifiche effettuate
### Modifiche al database:
- Rimossa la tabella `gestione` e aggiunto il campo `tutor` nella tabella `corso_personalizzato`, definito come chiave esterna verso la tabella `docente_tutor`.
- Rimossa la tabella `definizione` e aggiunto il campo `azienda` nella tabella `Classe`, definito come chiave esterna verso la tabella `azienda`.
- Aggiunto il campo `id_azienda` nella tabella `corso_a_catalogo`, definito come chiave esterna verso la tabella `azienda`.
- Modificare il mapping e schema per corso a catalogo
### Modifiche alle parti precedenti del progetto:
- Cambiata la cardinalità tra **Proposta** e **A Catalogo** da **1:1** a **1:N** (nello schema relazionale rimane invariato).
- Cardinalità tra **Gestione** e **Personalizzato** cambiata da **1:N** a **1:1**.

## Tasks
1. - [x] Registrazione di un corso a catalogo;
2. - [x] Iscrizione di un'azienda a una classe;
3. - [x] Richiesta di un corso personalizzato;
4. - [x] Aggiunta di un nuovo docente/tutor a una classe;
5. - [x] Modifica del docente/tutor a cui è affidato un corso personalizzato;
6. - [x] Stampa di tutti i corsi a catalogo messi a disposizione da un’azienda erogatrice;
7. - [x] Stampa di tutte le aziende erogatrici non impegnate in corsi personalizzati;
8. - [x] Verifica della possibilità di assegnare un tutor/docente a un corso;
9. - [x] Verifica l’eventuale presenza di tutor attualmente non coinvolti in corsi;
10. - [x] Per ciascun corso a catalogo, stampare il numero totale di discenti;
11. - [ ] Stampa i dati del docente/tutor maggiormente impiegato in corsi (a catalogo e/o personalizzato);
12. - [ ] Stampa di tutti i corsi a catalogo per i quali non si è mai formata più di una classe;
13. - [ ] Stampa dei dati delle aziende erogatrici, compreso il ricavo totale che hanno ottenuto dall’erogazione di tutte le tipologie di corsi;
14. - [x] Stampa di ogni classe, compreso il ricavo ottenuto mediante la definizione della stessa;
15. - [ ] Stampa una classifica delle aziende fruitrici sulla base del numero di servizi che ha richiesto (e.g., corsi di formazione personalizzati e/o corsi a catalogo).