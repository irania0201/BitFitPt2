package com.example.bitfit_pt1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDAO {
    @Query("SELECT * FROM entry_table")
    fun getAll(): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entry_table ORDER BY date Desc")
    fun getAllByDateDesc(): Flow<List<EntryEntity>>

    @Insert
    fun insert(entryEntity: EntryEntity)

    @Insert
    fun insertAll(entryList: List<EntryEntity>)

    @Query("DELETE FROM entry_table WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM entry_table")
    fun deleteAll()
}