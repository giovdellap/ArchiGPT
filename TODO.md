# TO FIX
- src/components/serviceOverviewTab.js: 'getServiceTypeColor' is assigned a value but never used  (handle microservices tag)


# MICROSERVICES BRANCH

## API HANDLER
Assistente Microservices (V)
Assistente Util_2: (V - da provare)
- Genera un array di User stories e ports range
Assistente Util_3: (V - da provare)
- Genera un json con:
  - name
  - type
  - description
  - ports

## BACKEND

### Container_3
1) Content per Container_3 (V)
2) chiamata ad assistant Container_3 (V)
3) mette la descrizione in microservices (V)
4) aggiorna status (V)

### Generate microservices
1) Content per Util_2 (V)
2) chiamata a Util_2 (V)
3) mette user stories e ports range nel DB (container document) (V)
4) Content per Util_3 (V)
5) chimata a Util_3 (V)
6) mette i json nel DB (container document) (V - aggiungere altri campi)
7) aggiorna status

# DOCUMENTI PER TIPO DI MICROSERVIZIO

services: [
	{
		name: 'service_1',
		type: 'backend',
		port: '101110',
		description: 'NO',
		specifications: 'NO',
		endpoints: 'NO'
	},
	{
		name: 'service_2',
		type: 'frontend',
		port: '101111',
		description: 'NO',
		specifications: 'NO',
		pages: 'NO'
	},
	{
		name: 'service_3',
		type: 'database',
		port: '101111',
		description: 'NO',
		specifications: 'NO'
	},
	{
		name: 'service_4',
		type: 'other',
		port: '101111',
		description: 'NO',
		specifications: 'NO'
	}
]



# GENERALE


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