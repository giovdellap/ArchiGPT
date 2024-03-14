#!/bin/bash
#This file is used to initialize some elements at statup

#waiting for kibana to properly startup
sleep ${SLEEP_TIME}

printf "\nEnd Sleep\n"

#purge old data
curl -X DELETE 'elasticsearch:9200/game'
curl -X DELETE 'elasticsearch:9200/questions'

printf "\nPurged Indexes\n"

#create needed indexes
curl -X PUT "elasticsearch:9200/game"
curl -X PUT "elasticsearch:9200/questions"

printf "\nCreated Indexes\n"

#create default entries
curl -X PUT -H "Content-Type:application/json" -d '{"nickname":"admin","score":0}' 'elasticsearch:9200/game/_doc/0'
curl -X PUT -H "Content-Type:application/json" -d '{"A":0, "B":0, "C":0, "D":0}' 'elasticsearch:9200/questions/_doc/0'

printf "\nCreated Default Entries\n"


#import dashboards in kibana
curl -X POST -H "kbn-xsrf: reporting" -H "Content-Type:application/json" -d "@kibana_dashboard_1.json" "kibana:5601/api/kibana/dashboards/import"
curl -X POST -H "kbn-xsrf: reporting" -H "Content-Type:application/json" -d "@kibana_dashboard_2.json" "kibana:5601/api/kibana/dashboards/import"

printf "\nDashboard Imported\n"