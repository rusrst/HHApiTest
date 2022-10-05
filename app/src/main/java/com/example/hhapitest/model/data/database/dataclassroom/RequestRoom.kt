package com.example.hhapitest.model.data.database.dataclassroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RequestRoom(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String? = "NAME",
    var request: String,
    val employerName: String?
)
