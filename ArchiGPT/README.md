## ArchiGPT

1) Analisi User Stories (GENERAL)
2) Design Container (GENERAL)
3) Assegnazione User Stories (GENERAL)
4) Assegnazione User Stories mancanti (GENERAL)
5) Eventuale aggiunta container (GENERAL)
6) Descrizione Container (Container purpose) (CONTAINER)
7) Design servizi
8) Services Interaction (?)
9) Service Description

## Assistants

### Archi_1
#### User Stories Analyzer

Classifica le User Stories per utente

### Archi_2
#### Container Designer

Propone i container con una breve descrizione per ognuno

### Archi_3
#### User Stories Classifier

Assegna un container ad ogni User Stories
3 possibilità:
- container esistente
- 2 o più container esistenti
- boh (non riesce ad assegnare)

### Archi_4
#### Missing User Stories Matcher

Controlla le User Stories orfane e le assegna ai container esistenti

### Archi_5
#### Container Proposer

Propone un container con una breve descrizione da creare per le user stories orfane

### Archi_6
#### Container Purpose Generator

Descrive ogni container dal punto di vista