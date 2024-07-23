## Final index #TODO
fi = 100*((r1/100))*(r2/100)


### INPUT:
n_us = numero user stories


### METRICHE:

- USER STORIES METRICS (1x)

### user stories satisfaction coverage
n_sod = user stories soddisfatte
r1 = 100 *(n_sod/n_us)

### idea tutte le user stories soddisfatte

Archi x ogni microservizio ritorna:
una tabella di endpoint con le seguenti colonne:
- url
- user story corrispondente
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


  
- CONTAINER METRICS (1x)

### container integrity coverage
n_ss = set di user stories completi
n_set = numero di set di user stories
r2 = 100*(n_ss/n_set)
idee:
    - dare un peso ai set (in base alla dimensione?)

### granularity index
n_set = numero di set di user stories
n_c = numero container con almeno una user story
ig = 100*(n_c/n_set) per ig>100 => ig = 100

### metrica numero container rispetto ai set (granularity index esteso n_c)
r3 = 100*(n_c/n_set) per n_c <= n_set
r3 = 100*((2*n_set-n_c)/n_set per n_c >= n_set

### ### metrica numero container rispetto ai set (granularity index esteso n_copt)
n_cl = numero cluster
r3 = 100*(n_c/n_cl) per n_c <= n_cl
r3 = 100 per n_cl < n_c < n_set>
r3 = 100*((2*n_set-n_c)/n_set per n_c >= n_set
  
- SERVICE METRICS (n_c x)

### service integrity coverage
n_serv_be = numero di servizi backend
n_set = numero di set assegnati al container
r2 = 100*(n_ss/n_set)



