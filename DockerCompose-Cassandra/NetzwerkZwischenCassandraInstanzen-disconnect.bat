
@REM Zeige Netzwerkkonfiguration aller Container vor Änderung
docker ps --format "table {{.Names}}\t{{.Networks}}"
                          
docker network disconnect dockercompose-cassandra_netzwerk-intern kinoprogramm-cassandra-1                        
docker restart kinoprogramm-cassandra-1

docker network disconnect dockercompose-cassandra_netzwerk-intern kinoprogramm-cassandra-2
docker restart kinoprogramm-cassandra-2

@REM Zeige Netzwerkkonfiguration aller Container nach Änderung
docker ps --format "table {{.Names}}\t{{.Networks}}"
