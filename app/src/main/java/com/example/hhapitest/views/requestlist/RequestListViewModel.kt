package com.example.hhapitest.views.requestlist


import androidx.lifecycle.SavedStateHandle
import com.example.foundation.model.*
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.hhapitest.model.internet.HhApiDataInternetRepository
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.database.RoomRepository
import com.example.hhapitest.model.database.dataclassroom.*
import com.example.hhapitest.model.json.dataclassesforjson.ListRequest
import com.example.hhapitest.model.json.dataclassesforjson.ShortItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.sql.Date


class RequestListViewModel(
    screen: RequestList.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val repository: HhApiDataInternetRepository,
    private val taskFactory: TaskFactory,
    savedStateHandle: SavedStateHandle,
    dispatcher: Dispatcher,
    private val roomRepository: RoomRepository
) : BaseViewModel(dispatcher, taskFactory) {

    var urlItem: String? = null
    var idItem: String? = null
    fun tryAgain(){
        load()
    }
        fun getListRequestFromUrl() = load()


        private val _data = MutableLiveResult<List<ShortItem>?>(PendingResult())
        val liveListShortItem: LiveResult<List<ShortItem>?> = _data
        fun launch (screen: BaseScreen, result: Any?){
            navigator.launch(screen, result)
        }

    private fun load(){
        if (urlItem == null) navigator.goBack()
        taskFactory.async {
            val data= repository.getRequestFromUrl(urlItem ?: "", null)
            val list = Json.decodeFromString<ListRequest>(data)
            val responseRoom =  ResponseRoom(department = Department(), area = ShortArea(), salary = ShortSalary(),
                type = TypeVacancy(), address = AddressRequest(), employer = Employer(), snippet = Snippet())
            list.items?.forEach {
                responseRoom.id = it.id ?: 0
                responseRoom.name = it.name
                responseRoom.department.apply {
                    id = it.department?.id
                    name = it.department?.name
                }
                responseRoom.area.apply {
                    id = it.area?.id
                    name = it.area?.name
                    url = it.area?.url
                }
                responseRoom.salary.apply {
                    from = it.salary?.from
                    to = it.salary?.to
                    currency = it.salary?.currency
                    gross = it.salary?.gross
                }
                responseRoom.type.apply {
                    id = it.type?.id
                    name = it.type?.name
                }
                responseRoom.address.apply {
                    city = it.address?.city
                    street = it.address?.street
                    building = it.address?.building
                    description = it.address?.description
                    lat = it.address?.lat
                    lng = it.address?.lng
                    raw = it.address?.raw
                    metro = it.address?.metro
                    id = it.address?.id
                }
                responseRoom.publishAt = it.published_at ?: ""
                responseRoom.url = it.url ?: ""
                responseRoom.altUrl = it.alternate_url ?: ""
                responseRoom.employer.apply {
                    id = it.employer?.id
                    name = it.employer?.name
                    url = it.employer?.url
                    alternate_url = it.employer?.alternate_url
                    vacancies_url = it.employer?.vacancies_url
                    trusted = it.employer?.trusted
                }
                responseRoom.snippet.apply {
                    requirement = it.snippet?.requirement
                    responsibility = it.snippet?.responsibility
                }
                responseRoom.createDB = Date(System.currentTimeMillis()).toString()
                responseRoom.updateDB = Date(System.currentTimeMillis()).toString()
                responseRoom.requestID = idItem?.toInt()
                roomRepository.insertResponseRoom(responseRoom)
            }
            return@async list.items
        }.into(_data)
    }
}

