#!/bin/bash

: "${FORKED:=}"
if [ -z "${FORKED}" ]; then
	echo >&2 'mongod for initdb is going to shutdown'
	mongod --pidfilepath /tmp/docker-entrypoint-temp-mongod.pid --shutdown
	echo >&2 'replica set will be initialized later'
	FORKED=1 ${0} &
	unset FORKED
	mongodHackedArgs=(:) # bypass mongod --shutdown in docker-entrypoint.sh
	return
fi

# FIXME: assume mongod listens on 127.0.0.1:27017
mongo=( mongosh -u admin -p password)

tries=30
while true; do
	sleep 1
	if "${mongo[@]}" --eval 'quit(0)' &> /dev/null; then
		# success!
		break
	fi
	(( tries-- ))
	if [ "$tries" -le 0 ]; then
		echo >&2
		echo >&2 'error: unable to initialize replica set'
		echo >&2
		kill -STOP 1 # initdb won't be executed twice, so fail loudly
		exit 1
	fi
done

echo 'about to initialize replica set'
# FIXME: hard-coded replica set name & member host
"${mongo[@]}" --eval 'var config = {
    "_id": "rs0",
    "version": 1,
    "members": [
        {
            "_id": 1,
            "host": "mongo1:27017",
            "priority": 2
        },
        {
            "_id": 2,
            "host": "mongo2:27017",
            "priority": 1
        },
        {
            "_id": 3,
            "host": "mongo3:27017",
            "priority": 1
        }
    ]
};
rs.initiate(config, { force: true });
'
echo "****** Waiting for 10 seconds for replicaset configuration to be applied ******"
sleep 10
"${mongo[@]}" --eval "$(cat /scripts/mongo-init.js)"
