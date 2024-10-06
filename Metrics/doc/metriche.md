## Final index #TODO
fi = 100*((r1/100))*(r2/100)


### INPUT:
num_us = numero user stories
num_set = numero di set di user stories
num_c = numero di container


### METRICHE:

## USER STORIES METRICS (1x)

### User Stories Satisfaction Coverage
us_sod = user stories soddisfatte
metric_result = 100 *(us_sod/num_us)
  
## CONTAINER METRICS (1x)

### Container Integrity Coverage
num_set_us = set di user stories completi
metric_result = 100*(num_set_us/num_set)

### Granularity Evaulation
num_clique = numero not overlapping cliques (found by maxNotOverllapingCliques.py)
                    if num_c <= num_clique             | = 100*(num_c/num_clique)
metric_result =     if num_clique < num_c < num_set    | = 100
                    if num_c >= num_set                | = 100*(2*num_set-num_c)/num_set 
  
## SERVICE METRICS (n_c x)

### Container Service Coverage (for each container)
num_serv_be = numero di servizi backend
num_clique_full = numero di clique fullfilled
num_set_full = numero di set fullfilled
                    if num_serv_be <= num_clique_full                  | = 100*(num_serv_be/num_clique_full)
metric_result =     if num_clique_full < num_serv_be < num_set_full    | = 100
                    if num_serv_be >= num_set_full                     | = 100*(2*num_set_full-num_serv_be)/num_set_full 

### Service Coverage
metric_result = sommatoria di (for each container, (1/n_c)*metric_result)

### Container Persistance Coverage
If a container contains a set with db = true, than this container must have at least a database microservice
(for each container, sommatoria di (1/n_c)*metric_result)

<!-- ### persistance coverage
n_cdb = numero di container con almeno un set con db = true
ssc = sommatoria di (Per ogni container con un set con db = true, (1/n_cdb)*scc) -->


## ENDPOINTS METRICS

### Container Endpoint Coverage (for each container)
num_us_c = numero user stories container c
num_us_e = numero user stories coperte da un endpoint e
metric_result = 100*((sommatoria di num_us_e)/num_us_c)

### System Container Endpoint Coverage 
metric_result = sommatoria di cec*(1/n_c) (For each container)




### idea tutte le user stories soddisfatte

Archi x ogni microservizio ritorna:
una tabella di endpoint con le seguenti colonne:
- url
- array di user story corrispondente
- lettura su DB
- scrittura su DB
- request object
- response object

Elenco di json degli oggetti request/response


Che fa l'algoritmo della metrica:
1) unisce tutte le tabelle endpoint in un'unico tabellone
2) fa un elenco di oggetti
3) Confronta per ogni user story la row della tabella con la row del tabella benchmark

Colonne tabella benchmark:
- user story corrispondente
- lettura su DB
- scrittura su DB
- request object
- response object

Confronta gli oggetti con quelli del benchmark

Oggetto esempio benchmark login request:
{
    string:1,
}

v_us = valore singola user story
n_err = numero errori singola user story

Per ogni user story, v_us = 1 - (0.1 * n_err)
r1 = 100 * (sommatoria v_us)/n_us
