## Final index #TODO
fi = 100*((r1/100))*(r2/100)


### INPUT:
n_us = numero user stories
n_set = numero di set di user stories
n_c = numero di container


### METRICHE:

- USER STORIES METRICS (1x)

### user stories satisfaction coverage
n_sod = user stories soddisfatte
r1 = 100 *(n_sod/n_us)
  
- CONTAINER METRICS (1x)

### container integrity coverage
n_ss = set di user stories completi
r2 = 100*(n_ss/n_set)

### ### granularity evaulation
n_cl = numero not overlapping cliques (found by maxNotOverllapingCliques.py)
r3 = 100*(n_c/n_cl) per n_c <= n_cl
r3 = 100 per n_cl < n_c < n_set
r3 = 100*((2*n_set-n_c)/n_set per n_c >= n_set
  
- SERVICE METRICS (n_c x)

### service coverage (per ogni container)
n_serv_be = numero di servizi backend
n_set = numero di set assegnati al container
scc = 100*(n_serv_be/n_set)

### container service coverage
ssc = sommatoria di (Per ogni container, (1/n_c)*scc)

### service user stories coverage
x = 



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
