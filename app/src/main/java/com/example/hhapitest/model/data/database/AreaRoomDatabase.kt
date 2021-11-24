package com.example.hhapitest.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hhapitest.model.data.AreaRoom

@Database(entities = [AreaRoom::class], version = 1, exportSchema = false)
abstract class AreaRoomDatabase: RoomDatabase() {
    abstract fun areaDAO(): AreaRoomDatabaseInterface
}