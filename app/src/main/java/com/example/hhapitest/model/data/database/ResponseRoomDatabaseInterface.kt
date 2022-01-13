package com.example.hhapitest.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.hhapitest.model.data.database.dataclassroom.RequestRoom
import com.example.hhapitest.model.data.database.dataclassroom.ResponsesRoom

@Dao
interface ResponseRoomDatabaseInterface {
    @Query("SELECT * FROM ResponsesRoom")
    fun getResponsesRoomLiveData (): LiveData<List<ResponsesRoom>?>
    @Query("SELECT * FROM ResponsesRoom")
    fun getResponsesRoom (): List<ResponsesRoom>?
}