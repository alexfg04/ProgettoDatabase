## Progetto Basi di Dati

### Modifiche effettuate
*Modifiche al database*:
- Rimossa la tabella Gestione e aggiunto il campo tutor come chiave esterna in Corso_Personalizzato
- Rimosso la tabella Definizione e aggiunto il campo azienda come chiave esterna in Classe

*Modifiche alle parti precedenti del progetto*:
- Cambiata la cardinalità tra Proposta e A Catalogo da 1:1 a 1:N (nello schema relazione rimane invariato).
- Cardinalità tra Gestione e Personalizzato cambiata da 1:N a 1:1.