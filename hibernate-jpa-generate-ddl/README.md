hibernate-jpa-generate-ddl
==================================

In these project I use `hibernate3-maven-plugin` version 2.2 to generate DDL starting from JPA entities of an existing project

You must run `mvn clean package` and the `schema.sql` get generated in the folder `target/hibernate3/sql`.

The  generated DDL is related to `hibernate-sample-with-derbydb` project.