## GENERALE

Aggiungere campi:
	- 

- aggiungere campi a db e fe:
	- System -> description (solo DB)
	- Container -> ports range (in ContainerDesigner)

- aggiungere barra degli strumenti in FE, che raccoglierà dei possibili strumenti utili per la rigenerazione di messaggi. Esempio:
	- Regenerate -> rigenera un messaggio, cancellando quello vecchio
	- Edit -> lo user può modificare il messaggio da FE, e inviarlo al BE. In questo caso servirebbe un helper assistant che in input
		abbia il nuovo e vecchio messaggio, riesca ad individuare i cambiamenti e a classificarli come semplici modifiche (sintassi,
		grammatica,..) oppure modifiche progettuali (nomi dei container/servizi, aggiunta o rimozione di container/servizi) cioè
		modifiche che generano cambiamenti a cascata.
	- Erase -> cancella semplicemente il messaggio

	Tutti i cambiamenti sono da intendere come semplici modifiche ai message esistenti, tranne per documenti importanti come
	quelli che si occupano della generazione dei container e dei servizi, in quel caso verrebbe modificata l'intera struttura del 
	progetto!