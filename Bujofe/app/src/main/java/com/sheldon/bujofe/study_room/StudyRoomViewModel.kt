package com.sheldon.bujofe.study_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sheldon.bujofe.`object`.Weekday

class StudyRoomViewModel : ViewModel() {



    val selectedWeekday = MutableLiveData<String>()

}
