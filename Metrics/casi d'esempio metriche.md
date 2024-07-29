ARCHI DICE:
Container 1: Auth
US: 1, 2, 3, 4
endpoint container 1 coprono solo 1, 2, 3, manca 4
microservizi: be e db
tabella DataMetrics:
| POST | 1, 2 |
| POST | 3 |
| POST | 4 |

tabella Endpoint Archi:
| POST | 1, 2 |
| POST | 3 |

Set: 1, 2, 3, 4

## CASO 2
ARCHI DICE:
Container 1: Auth
US: 1, 2, 3, 4
endpoint container 1 coprono solo 1, 2, 3, 4
microservizi: 1xbe e 1xdb
tabella Endpoint Archi:
| POST | 1, 2 | 
| POST | 3 |
| POST | 4 |

BENCHMARK: 

Set1: 1, 2, 3, 4
Set2: 5, 6
Set3: 7, 8
tabella DataMetrics Set 1:
| POST | 1, 2 |
| POST | 3 |
| POST | 4 |

tabella DataMetrics Set 2:
| POST | 5, 6 |

tabella DataMetrics Set 3:
| POST | 7, 8 |

container integrity coverage: 33%
user stories satisfaction: 50%
container service coverage: 100%

## CASO 3
ARCHI DICE:
Container 1: Auth
US: 1, 2, 3, 4, 5, 6
microservizi: 1xbe e 1xdb
tabella Endpoint Archi:
| POST | 1, 2 | WRITE
| POST | 3 | READ
| POST | 4 |
| POST | 5 | WRITE
| POST | 6 | READ

BENCHMARK: 

Set1: 1, 2, 3, 4
Set2: 5, 6
Set3: 7, 8
tabella DataMetrics Set 1:
| POST | 1, 2 | WRITE
| POST | 3 | READ
| POST | 4 |

tabella DataMetrics Set 2:
| POST | 5 | WRITE
| POST | 6 | READ

tabella DataMetrics Set 3:
| POST | 7, 8 |

GRANULARITY EVALUATION: 50%
container integrity coverage: 66%
user stories satisfaction: 80%
service coverage container 1: 50%
container service coverage (media): 50%

## CASO 4
ARCHI DICE:
Container 1: Auth
US: 1, 2, 3, 4
microservizi: 1xbe e 1xdb
tabella Endpoint Archi:
| POST | 1, 2 | WRITE
| POST | 3 | READ
| POST | 4 |


Container 2: Pippotti
US: 5, 6
microservizi: 1xbe e 1xdb
tabella Endpoint Archi:
| POST | 5 | WRITE
| POST | 6 | READ

BENCHMARK: 

Set1: 1, 2, 3, 4
Set2: 5, 6
Set3: 7, 8
tabella DataMetrics Set 1:
| POST | 1, 2 | WRITE
| POST | 3 | READ
| POST | 4 |

tabella DataMetrics Set 2:
| POST | 5 | WRITE
| POST | 6 | READ

tabella DataMetrics Set 3:
| POST | 7, 8 |

GRANULARITY EVALUATION: 100%
container integrity coverage: 66%
user stories satisfaction: 80%
service coverage container 1: 100%
service coverage container 2: 100%
container service coverage (media): 100%


## CASO 5
ARCHI DICE:
Container 1: Auth
US: 1, 2, 3, 4, 5, 6
microservizi: 2xbe e 2xdb
tabella Endpoint Archi:
| POST | 1, 2 | WRITE | be1 |
| POST | 3 | READ | be1 |
| POST | 4 | be1 |
| POST | 5 | WRITE |be1 |
| POST | 6 | READ |be1 |

BENCHMARK: 

Set1: 1, 2, 3, 4
Set2: 5, 6
Set3: 7, 8
tabella DataMetrics Set 1:
| POST | 1, 2 | WRITE
| POST | 3 | READ
| POST | 4 |

tabella DataMetrics Set 2:
| POST | 5 | WRITE
| POST | 6 | READ

tabella DataMetrics Set 3:
| POST | 7, 8 |

GRANULARITY EVALUATION: 50%
container integrity coverage: 66%
user stories satisfaction: 80%
service coverage container 1: 50%
container service coverage (media): 50%