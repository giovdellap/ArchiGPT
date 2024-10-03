pd = progetto del dataset  
fi = formato pd input

## USER INPUT
- Nome modello  
- 1 json fi per ogni pd

## OUTPUT
Punteggio totale  
Per ogni pd:
- Punteggio totale
- Punteggio per ogni metrica

## TODO

## GENERIC
- definire formato user input model - OK
- definire formato fi - OK
- definire formato output - OK

### ARCHIGPT
- mettere generazione automatica di tutto in una volta
- generazione output json fi

### METRICS
- Scaffolding serverino node e docker -OK
- Scaffolding frontend (?)
- Chiamata nuovo modello
  - get projects from request
  - for each project
    - prende il 