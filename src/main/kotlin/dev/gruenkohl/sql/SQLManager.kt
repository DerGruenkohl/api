package dev.gruenkohl.sql

import dev.gruenkohl.*
import java.sql.Connection
import java.sql.DriverManager

abstract class SQLManager {
    val url = if(test) {
        "jdbc:mariadb://$testdb?" +
                "user=$dbuser&password=${dbpw}&allowPublicKeyRetrieval=true&useSSL=false"
    }else{
        "jdbc:mariadb://$dburl?" +
                "user=$dbuser&password=${dbpw}&allowPublicKeyRetrieval=true&useSSL=false"
    }
    val connection: Connection by lazy {
        DriverManager.getConnection(url)
    }
}