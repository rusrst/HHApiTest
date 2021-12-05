package com.example.hhapitest.model.data.database

import android.content.Context
import androidx.room.Room
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.TaskListener
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.hhapitest.model.data.AreaRoom

private const val DATABASE_NAME = "AreaDatabase"
class RoomRepository (context: Context, private val taskFactory: TaskFactory, private val dispatcher: com.example.foundation.model.tasks.dispatchers.Dispatcher) {
    private val database: AreaRoomDatabase = Room.databaseBuilder(
        context.applicationContext,
        AreaRoomDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val databaseDAO = database.areaDAO()
    fun addAreaRoom (areaRoom: AreaRoom){
         taskFactory.async {
             try {
                 databaseDAO.addAreaRoom(areaRoom)
             }
            catch (e: Exception) {
            }
        }.safeEnqueue()
    }

    fun addAreasRoom (listAreasRoom: List<AreaRoom>){
        taskFactory.async {
            try {
                listAreasRoom.forEach { areaRoom->
                    databaseDAO.addAreaRoom(areaRoom)
                }
            }
            catch (e: Exception) {
            }
        }.safeEnqueue()
        }
    fun checkDatabaseOfAreas (): Task<AreaRoom?>{
       return taskFactory.async {
            try {
                return@async databaseDAO.getAreaFromRoomNoLiveData(113)
            }
            catch (e: Exception){
                throw Exception ("ERROR ACCESS  DATABASE")
            }
        }
    }

    fun getAreasOnNameFromRoomNoLiveData(str:String) = databaseDAO.getAreasOnNameFromRoomNoLiveData(str)


    private val tasks = mutableSetOf<Task<*>>()

    private fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null){
        tasks.add(this)
        this.enqueue(MainThreadDispatcher()){
            tasks.remove(this)
            listener?.invoke(it)
        }
    }
}