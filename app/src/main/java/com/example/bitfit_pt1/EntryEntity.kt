package com.example.bitfit_pt1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "entry_table")
data class EntryEntity(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "date") val date: Instant?,
    @ColumnInfo(name = "entry") val entry: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)