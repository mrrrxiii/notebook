package com.example.notebook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface NotebookDatabaseDao {
    /**
     * Defines methods for using the SleepNight class with Room.
     */


        @Insert
        suspend fun insert(notebook: Notebook)

        /**
         * When updating a row with a value already set in a column,
         * replaces the old value with the new one.
         *
         * @param night new value to write
         */
        @Update
        suspend fun update(notebook: Notebook)

        /**
         * Selects and returns the row that matches the supplied start time, which is our key.
         *
         * @param key startTimeMilli to match
         */
        @Query("SELECT * from notebook_table WHERE notebookId = :key")
        suspend fun get(key: Long): Notebook?

        /**
         * Deletes all values from the table.
         *
         * This does not delete the table, only its contents.
         */
        @Query("DELETE FROM notebook_table")
        suspend fun clear()

        /**
         * Selects and returns all rows in the table,
         *
         * sorted by start time in descending order.
         */
        @Query("SELECT * FROM notebook_table ORDER BY notebookId DESC")
        fun getAllNights(): LiveData<List<Notebook>>

        /**
         * Selects and returns the latest night.
         */
        @Query("SELECT * FROM notebook_table ORDER BY notebookId DESC LIMIT 1")
        suspend fun getlateast(): Notebook?


}