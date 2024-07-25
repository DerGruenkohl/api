package dev.gruenkohl.sql

import dev.gruenkohl.model.Link
import dev.gruenkohl.model.Tracking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.SQLException

class TrackSQLManager(private val link: Link): SQLManager() {
    fun addTrack(tracking: Tracking){
        if (checkTrack()){
            updateTrack(tracking)
            return
        }
        val insertSQL = "INSERT INTO Tracker (uuid, profile, data) VALUES (?,?,?)"
        connection.prepareStatement(insertSQL).use { preparedStatement ->
            preparedStatement.setString(1, link.uuid)
            preparedStatement.setString(2, link.settings.profileID)
            preparedStatement.setString(3, Json.encodeToString(tracking))
            preparedStatement.executeUpdate()
        }
        println("Successfully added")
    }
    private fun updateTrack(tracking: Tracking){
        try {
            connection.prepareStatement(
                """
        UPDATE Tracker
        SET data = ?
        WHERE uuid LIKE ?
      """.trimIndent()
            ).use { statement ->
                statement.setString(1, Json.encodeToString(tracking))
                statement.setString(2, link.uuid)
                val rowsInserted = statement.executeUpdate()
                println("successfully updated")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
    fun getTrack(): Tracking {
        var username = ""

        connection.prepareStatement(
            """
        SELECT *
        FROM Tracker
        WHERE uuid LIKE ?
      
      """.trimIndent()
        ).use { statement ->
            statement.setString(1, link.uuid)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                username = resultSet.getString(3)
            }
        }
        return Json.decodeFromString<Tracking>(username)
    }

    fun checkTrack(): Boolean{
        connection.prepareStatement(
            """
        SELECT *
        FROM Tracker
        WHERE uuid LIKE ?
      
      """.trimIndent()
        ).use { statement ->
            statement.setString(1, link.uuid)
            val resultSet = statement.executeQuery()
            var empty = true
            while (resultSet.next()) {
                empty = false
            }
            return !empty
        }
    }
}