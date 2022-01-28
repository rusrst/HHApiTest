package com.example.hhapitest.model.database.dataclassroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AreaRoom(
    @PrimaryKey var id: Int,
    var parentId: String?,
    var name: String?)