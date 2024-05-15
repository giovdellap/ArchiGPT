1) crea progetto da frontend
2) lui crea una collection e ci mette:
	- un documento status con i primi 5 a NO
	- un documento general vuoto
3) FE cambia pagina e chiama getStatus(project_name) per vedere come fare il menù di sinistra
4) FE carica il menù di sinistra con i primi 5 rossi
5) l'utente clicca il pulsante genera in basso e FE chiama /UserStoriesAnalyzer
6) BE interroga l'assistente
7) scrive OK IN status[steps][status] dove status[steps][name] è quello dell'endpoint
8) scrive la risposta in general[UserStoriesAnalyzer]
9) se l'utente clicca di nuovo su genera, riparti da 6 (alla 8 sovrascrivi general[UserStoriesAnalyzer])




NOTE:

Il bottone genera chiama l'endpoint per generare in base allo step in cui si trova
Es: si trova su UserStoriesAnalyzer e chiama /project/UserStoriesAnalyzer[POST]

Il click sul menù a sinistra è consentito solo su quelle verdi e sulla prima rossa
Il FE al click va sulla pagina dello step cliccato
Se lo step cliccato è verde, chiama /project/nomeStep[GET] a BE per prendersi il messaggio di quello step
Es: click su UserStoriesAnalyzer e lui chima /project/UserStoriesAnalyzer[GET]


DOCUMENTI IN PROJECT COLLECTION:
1)status
	{
		"steps": [
			{
				"name": "UserStoriesAnal",
				"status": OK,
			},
			{
				"name": "ContainerDesigner",
				"status": OK
		],
		"containers": [
			{
				"name": "CONTAINER_A",
				"id_doc": "AO",
				"containerDescription": OK,
				"containerTechnologies": OK
				"services": [
					{
						"name": "Service_1",
						"endpoints": OK,
						"datastructures": OK
					}
				]
			}
		]		
	}

2) steps
	{
		"UserStoriesAnal": "adaasd",
		"ContainerDesigner" "..."
	}

3) CONTAINER_A 
	{
		"containerDescription": "dsffsf",
		"containerTechnologies": "sdfsdf",
		"services": [
			"endpoints": "sdfdsf"
			"data structures": "safsadf"
		]
	}

4) CONTAINER_B 
	{
		"containerDescription": "dsffsf",
		"containerTechnologies": "sdfsdf",
		"services": [
			"endpoints": "sdfdsf"
			"data structures": "safsadf"
		]
	}