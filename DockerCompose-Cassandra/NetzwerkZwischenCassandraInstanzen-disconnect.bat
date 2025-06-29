
docker network ls

@REM Erst Container aus Netzwerk entfernen, dann Netzwerk loeschen
docker network disconnect dockercompose-cassandra_netzwerk-intern kinoprogramm-cassandra-1
docker network disconnect dockercompose-cassandra_netzwerk-intern kinoprogramm-cassandra-2                          
docker network rm dockercompose-cassandra_netzwerk-intern                          

@REM docker restart kinoprogramm-cassandra-1
@REM docker restart kinoprogramm-cassandra-2

docker network ls
