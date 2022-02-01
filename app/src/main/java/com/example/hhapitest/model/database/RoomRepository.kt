package com.example.hhapitest.model.database

import android.content.Context
import androidx.room.Room
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.TaskListener
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.hhapitest.model.database.dataclassroom.AreaRoom
import com.example.hhapitest.model.database.dataclassroom.ResponseRoom

private const val DATABASE_NAME = "AreaDatabase.db"
class RoomRepository (context: Context, private val taskFactory: TaskFactory, private val dispatcher: com.example.foundation.model.tasks.dispatchers.Dispatcher) {
    private val database: AreaRoomDatabase = Room.databaseBuilder(
        context.applicationContext,
        AreaRoomDatabase::class.java,
        DATABASE_NAME
    )
        .createFromAsset("myDB.db")
        .build()

    private val databaseAreaDAO = database.areaDAO()
    private val requestDAO = database.requestDAO()
    private val responseDAO = database.responseDao()

    fun addAreasRoom (listAreasRoom: List<AreaRoom>){
        try {
            listAreasRoom.forEach { areaRoom->
                databaseAreaDAO.addAreaRoom(areaRoom)
            }
        }
        catch (e: Exception) {
        }
    }

    fun checkDatabaseOfAreas (): Task<AreaRoom?>{
       return taskFactory.async {
            try {
                return@async databaseAreaDAO.getAreaFromRoom(113)
            }
            catch (e: Exception){
                throw Exception ("ERROR ACCESS  DATABASE")
            }
        }
    }

    fun getAreasOnNameFromRoomNoLiveData(str:String) = databaseAreaDAO.getAreasOnNameFromRoom(str)

    fun getListRequests() = requestDAO.getRequestsRoomLiveData()

    fun insertResponseRoom (responseRoom: ResponseRoom) = responseDAO.insertResponseRoom(responseRoom)

    private val tasks = mutableSetOf<Task<*>>()

    private fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null){
        tasks.add(this)
        this.enqueue(MainThreadDispatcher()){
            tasks.remove(this)
            listener?.invoke(it)
        }
    }
}