package dev.gruenkohl.sql

import dev.gruenkohl.model.Link
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.SQLException

class LinkManager : SQLManager(){
    fun addLink(link: Link){
        if (checkLink(link)){
            updateLink(link, link.discordId)
            return
        }
        val insertSQL = "INSERT INTO Users (discordId, data) VALUES (?, ?)"
        connection.prepareStatement(insertSQL).use { preparedStatement ->
            preparedStatement.setLong(1, link.discordId)
            preparedStatement.setString(2, Json.encodeToString(link))
            preparedStatement.executeUpdate()
        }
        println("Successfully added")
    }
    private fun updateLink(link: Link, id: Long){
        try {
            connection.prepareStatement(
                """
        UPDATE Users
        SET data = ?
        WHERE discordId LIKE ?
      """.trimIndent()
            ).use { statement ->
                statement.setString(1, Json.encodeToString(link))
                statement.setLong(2, id)
                val rowsInserted = statement.executeUpdate()
                println("successfully updated")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
    fun getLink(discordId: Long): Link{
        var username = ""

        connection.prepareStatement(
            """
        SELECT *
        FROM Users
        WHERE discordId LIKE ?
      
      """.trimIndent()
        ).use { statement ->
            statement.setLong(1, discordId)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                username = resultSet.getString(2)
            }
        }
        return Json.decodeFromString<Link>(username)
    }
    fun getAllLinks(): List<Link>{
        var links = mutableListOf<Link>()

        connection.prepareStatement(
            """
        SELECT *
        FROM Users
      """.trimIndent()
        ).use { statement ->
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
               links.add(Json.decodeFromString<Link>(resultSet.getString(2)))
            }
        }
        return links
    }

    private fun checkLink(link: Link): Boolean{
        connection.prepareStatement(
            """
        SELECT *
        FROM Users
        WHERE discordId LIKE ?
      
      """.trimIndent()
        ).use { statement ->
            statement.setLong(1, link.discordId)
            val resultSet = statement.executeQuery()
            var empty = true
            while (resultSet.next()) {
                empty = false
            }
            return !empty
        }
    }
}