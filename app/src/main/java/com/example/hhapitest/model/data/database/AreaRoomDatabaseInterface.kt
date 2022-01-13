package com.example.hhapitest.model.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.hhapitest.model.data.database.dataclassroom.AreaRoom
import com.example.hhapitest.model.data.database.dataclassroom.RequestRoom

@Dao
interface AreaRoomDatabaseInterface {
    @Query ("SELECT * FROM areaRoom")
        fun getAreasFromRoomLiveData (): LiveData<List<AreaRoom>>
    @Query ("SELECT * FROM areaRoom")
        fun getAreasFromRoom (): List<AreaRoom>
    @Query ("SELECT * FROM areaRoom WHERE `id` = (:id)")
        fun getAreaFromRoomLiveData (id: Int): LiveData<AreaRoom?>
    @Query ("SELECT * FROM areaRoom WHERE `id` = (:id)")
        fun getAreaFromRoom (id: Int): AreaRoom?
    @Query ("SELECT * FROM areaRoom WHERE `name` LIKE (:str) LIMIT 5")
    fun getAreasOnNameFromRoom (str: String): List<AreaRoom>?
    @Update
        fun updateAreaRoom (areaRoom: AreaRoom)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun addAreaRoom(areaRoom: AreaRoom)

}