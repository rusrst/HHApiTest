package com.example.hhapitest.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hhapitest.model.database.dataclassroom.ResponsesRoom

@Dao
interface ResponseRoomDatabaseInterface {
    @Query("SELECT * FROM ResponsesRoom")
    fun getListResponseRoomLiveData (): LiveData<List<ResponsesRoom>?>
    @Query("SELECT * FROM ResponsesRoom")
    fun getListResponseRoom (): List<ResponsesRoom>?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertResponseRoom (responsesRoom: ResponsesRoom)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListResponseRoom (listResponsesRoom: List<ResponsesRoom>)
}