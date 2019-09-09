package com.sheldon.bujofe.studyroom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudyRoomViewModel : ViewModel() {

    val selectedWeekday = MutableLiveData<String>()

}
