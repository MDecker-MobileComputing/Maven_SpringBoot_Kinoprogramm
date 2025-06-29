# Docker-Container #

<br>

Dieses Verzeichnis enthält eine Datei [docker-compose.yml](docker-compose.yml),
die folgende Container definiert:

* Cassandra (Datenbank)
* Cassandra Web (Admin-UI)

<br>

## Cassandra Web ##

<br>

Web-UI für Cassandra, siehe auch: https://github.com/avalanche123/cassandra-web

Verbindet sich beim Start automatisch mit der Cassandra-Instanz.

<br>

Beispiel für Query, die man mit diesem Web-UI direkt ausführen kann:
```
select * from kino.kinoprogramm
```

<br>