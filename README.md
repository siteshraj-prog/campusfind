 # CampusFind

CampusFind is a terminal-based campus lost-and-found application using SQLite.

## Run

Requires Java 25+ and Maven.

```text
mvn clean test
mvn package
mvn compile exec:java
```

The database is created automatically at `data/campusfind.db`.
