package com.example.hhapitest.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hhapitest.model.database.dataclassroom.ResponseRoom

@Dao
interface ResponseRoomDatabaseInterface {
    @Query("SELECT * FROM ResponseRoom")
    fun getListResponseRoomLiveData (): LiveData<List<ResponseRoom>?>
    @Query("SELECT * FROM ResponseRoom")
    fun getListResponseRoom (): List<ResponseRoom>?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertResponseRoom (responseRoom: ResponseRoom)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListResponseRoom (listResponseRoom: List<ResponseRoom>)
}