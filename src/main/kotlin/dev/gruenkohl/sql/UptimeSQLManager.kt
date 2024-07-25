package dev.gruenkohl.sql


import dev.gruenkohl.hypixel.ExpApi
import dev.gruenkohl.model.Guild
import java.sql.SQLException
import java.sql.Timestamp
import java.time.LocalDateTime

class UptimeSQLManager: SQLManager() {
    private fun addNewGuild(id: String, expData: String){
        val insertSQL = "INSERT INTO ExpHistory (guildID, lastUpdated, expData) VALUES (?,?,?)"
        connection.prepareStatement(insertSQL).use { preparedStatement ->
            preparedStatement.setString(1, id)
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()))
            preparedStatement.setString(3, expData)
            preparedStatement.executeUpdate()
        }
        println("Successfully added")
    }

    fun addOrUpdateGuild(id: String){
        val api = ExpApi()
        if (!isGuildSaved(id)){
            addNewGuild(id, api.getExpHistoryFromApi(id))
        }else{
            sendServer(id, api.getExpHistoryFromApi(id))
        }
    }

    fun isGuildSaved(id: String): Boolean{
        connection.prepareStatement(
            """
        SELECT *
        FROM ExpHistory
        WHERE guildID LIKE ?
      
      """.trimIndent()
        ).use { statement ->
            statement.setString(1, id)
            val resultSet = statement.executeQuery()
            var empty = true
            while (resultSet.next()) {
                empty = false
            }
            return !empty
        }
    }

    fun sendServer(id: String, json: String){
        try {
            connection.prepareStatement(
                """
        UPDATE ExpHistory
        SET expData = ?, lastUpdated = ?
        WHERE guildID LIKE ?
      """.trimIndent()
            ).use { statement ->
                statement.setString(1, json)
                statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()))
                statement.setString(3, id)
                val rowsInserted = statement.executeUpdate()
                println("successfully updated")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getExpFromDtb(id: String): Pair<String, LocalDateTime>{
        var cfg = ""
        var timestamp = LocalDateTime.now()
        connection.prepareStatement(
            """
        SELECT *
        FROM ExpHistory
        WHERE guildID LIKE ?
      
      """.trimIndent()
        ).use { statement ->
            statement.setString(1, id)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                cfg = resultSet.getString(3)
                timestamp = resultSet.getTimestamp(2).toLocalDateTime()

            }
        }
        return Pair(cfg, timestamp)
    }

    fun getAllSavedGuilds() :List<Guild>{
        val api = ExpApi()
        val list = ArrayList<Guild>()
        connection.prepareStatement(
            """
        SELECT guildID
        FROM ExpHistory
      
      """.trimIndent()
        ).use {statement ->
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                list.add(api.getExpHistory(resultSet.getString(1)))
            }
        }

        return list
    }
}