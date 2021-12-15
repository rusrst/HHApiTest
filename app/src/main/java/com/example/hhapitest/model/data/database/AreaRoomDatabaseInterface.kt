package com.example.hhapitest.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hhapitest.model.data.database.dataclassroom.RequestRoom
import com.example.hhapitest.model.data.dataclassesforjson.AreaRoom

@Dao
interface AreaRoomDatabaseInterface {
    @Query ("SELECT * FROM areaRoom")
        fun getAreasFromRoomLiveData (): LiveData<List<AreaRoom>>
    @Query ("SELECT * FROM areaRoom")
        fun getAreasFromRoomNoLiveData (): List<AreaRoom>
    @Query ("SELECT * FROM areaRoom WHERE `id` = (:id)")
        fun getAreaFromRoomLiveData (id: Int): LiveData<AreaRoom?>
    @Query ("SELECT * FROM areaRoom WHERE `id` = (:id)")
        fun getAreaFromRoomNoLiveData (id: Int): AreaRoom?
    @Query ("SELECT * FROM areaRoom WHERE `name` LIKE (:str) LIMIT 5")
    fun getAreasOnNameFromRoomNoLiveData (str: String): List<AreaRoom>?
    @Update
        fun updateAreaRoom (areaRoom: AreaRoom)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun addAreaRoom(areaRoom: AreaRoom)


    @Query ("SELECT * FROM list_request")
    fun getRequestsRoomLiveData (): LiveData<List<RequestRoom>>
}