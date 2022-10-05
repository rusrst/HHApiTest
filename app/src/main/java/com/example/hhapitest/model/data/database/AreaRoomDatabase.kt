package com.example.hhapitest.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hhapitest.model.data.database.dataclassroom.AreaRoom
import com.example.hhapitest.model.data.database.dataclassroom.RequestRoom
import com.example.hhapitest.model.data.database.dataclassroom.ResponsesRoom

@Database(
    entities = [AreaRoom::class,
        RequestRoom::class, ResponsesRoom::class],
    version = 1
)
abstract class AreaRoomDatabase : RoomDatabase() {
    abstract fun areaDAO(): AreaRoomDatabaseInterface
    abstract fun requestDAO(): RequestRoomDatabaseInterface
}