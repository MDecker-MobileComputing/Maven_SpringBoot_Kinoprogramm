
@REM Gossip-Protokoll auf beiden Knoten deaktiveren, damit
@REM die Knoten keine Datensätze untereinander syncen
docker exec -it kinoprogramm-cassandra-1 nodetool disablegossip
docker exec -it kinoprogramm-cassandra-2 nodetool disablegossip

@REM Status abfragen: nodetool statusgossip
@REM Ausgabe, wenn       aktiv: running
@REM Ausgabe, wenn nicht aktiv: not running
