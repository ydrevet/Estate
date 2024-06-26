# Estate
Backend correspondant au projet 3 du parcours "Développeur d’applications Fullstack Java et Angular" d'OpenClassroom.

## Prérequis

- JDK 21
- Une installation de MariaDB ou MySQL

## Préparation de la base de données

Créer une base de données et un utilisateur pour l'application.
Exemple pour une base de données "chatop" et un utilisateur "chatop" avec pour mot de passe "chatop" (Ne pas reporduire en production !).

```mysql
CREATE DATABASE chatop CHARSET utf8mb4;
CREATE USER 'chatop'@'%' IDENTIFIED BY 'chatop';
GRANT ALL PRIVILEGES ON chatop.* TO 'chatop'@'%';
```

Les tables de la base de données seront initialisées par Flyway lors de l’initialisation de l’application.

## Génération d’une paire de clés publique / privée

Une paire de clés RSA est nécessaire pour la signature des tokens JWT. 
Deux fichiers sont nécessaires :
- `src/main/resources/priv.pem` : clé privée
- `src/main/resources/pub.pem` : clé publique

Ces fichiers peuvent être générés avec la commande suivante sur un système UNIX disposant d’OpenSSL :
```bash
openssl genpkey -quiet -algorithm rsa | tee priv.pem | openssl pkey -pubout -out pub.pem
```

Si OpenSSL n’est pas disponible sur votre machine, [un outil en ligne](https://cryptotools.net/rsagen) peut générer cette paire de clés.

## Lancement de l'application

Trois variables d'environnement doivent être définies avant le lancement de l’application.
- DATABASE_URL, définit l’URL de la base de données, au format JDBC
- DATABASE_USER, le nom de l’utilisateur lié à la base de données
- DATABASE_PASSWD, le mot de passe de l’utilisateur lié à la base de données

Une fois ces variales définies, l’application peut être lancée via le goal Maven `spring-boot:run`. Elle écoutera alors sur le port 3001.

Exemple sous Windows :
```powershell
$env:DATABASE_URL="jdbc:mariadb://localhost:3306/chatop"
$env:DATABASE_USER="chatop"
$env:DATABASE_PASSWD="chatop"
./mvnw spring-boot:run
```

Exemple sous Linux et macOS :
```bash
DATABASE_URL=jdbc:mariadb://localhost:3306/chatop DATABASE_USER=chatop DATABASE_PASSWD=chatop ./mvnw spring-boot:run
```

## Accès à la documentation Swagger UI

Une fois l’application lancée, il est possible d’accèder à Swagger UI depuis l’addresse [http://localhost:3001/swagger-ui.html](http://localhost:3001/swagger-ui.html)