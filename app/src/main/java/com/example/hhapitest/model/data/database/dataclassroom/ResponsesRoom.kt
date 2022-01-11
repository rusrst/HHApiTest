package com.example.hhapitest.model.data.database.dataclassroom

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.hhapitest.model.data.dataclassesforjson.*

@Entity(
    primaryKeys = [
        "id","createDB"
    ],
    indices = [
        Index("createDB"),
        Index("requestID")
    ],
    foreignKeys = [
        ForeignKey(entity = RequestRoom::class,
                    parentColumns = ["id"],
                    childColumns = ["requestID"],
                    onUpdate = ForeignKey.CASCADE,
                    onDelete = ForeignKey.SET_NULL)
    ]
)
class ResponsesRoom (val id: String, val name: String,
                     val departament: Department? = null,
                     val area: ShortArea? = null,
                     val salary: ShortSalary? = null,
                     val type: TypeVacancy? = null,
                     val adress: AddressRequest? = null,
                     val publishAt: String,
                     val url: String, val altUrl: String,
                     val employer: Employer? = null,
                     val snippet: Snippet? = null,
                     val createDB: String,
                     val updateDB: String,
                     val requestID: Int
)