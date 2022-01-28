package com.example.hhapitest.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.hhapitest.model.database.dataclassroom.RequestRoom

@Dao
interface RequestRoomDatabaseInterface {
    @Query("SELECT * FROM RequestRoom")
    fun getRequestsRoomLiveData (): LiveData<List<RequestRoom>?>
}