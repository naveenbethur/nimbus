#!/bin/bash
if [ $# != 3 ]
then
        echo -e "Usage: $0 <name> <port> <type>"
	exit -1
fi

. "$NIMBUS_HOME/bin/nimbus-env.sh"

NAME=$1
PORT=$2
TYPE=$3
LOG_FILE=$LOG_FILE/nimbus-$NAME-$USER-$TYPE.log

echo "starting nimbus using $CONFIG_FILE on port $PORT... logging to $LOG_DIR$LOG_FILE"

for server in `cat "$HOSTLIST"|sed  "s/#.*$//;/^$/d"`
do
	ssh $server "mkdir -p $LOG_DIR"
	ssh $server "$NIMBUS_EXEC $NIMBUS_JAVA_OPTS -Djava.library.path=$NIMBUS_HOME/bin/native $NIMBUS_HOME/bin/nimbus.jar --config $CONFIG_FILE -n $NAME -p $PORT -t $TYPE > $LOG_DIR$LOG_FILE 2>&1 &"
	ssh $server "mkdir -p $NIMBUS_HOME/pids"
	ssh $server "ps -eo pid,args | grep \"\-n $NAME \-p $PORT \-t $TYPE\" | grep -v grep | cut -c1-6 > $NIMBUS_HOME/pids/$NAME.pid"
done