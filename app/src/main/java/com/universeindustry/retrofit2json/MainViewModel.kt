package com.universeindustry.retrofit2json

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.universeindustry.retrofit2json.utils.extensions.Strings

class MainViewModel : ViewModel(){
    private val _fragmentType = MutableLiveData<String>("")
    val getFragmentType: LiveData<String>
        get() = _fragmentType

    fun setFragmentType(type : String){
        this._fragmentType.value = type
    }

    init {
        _fragmentType.value = Strings.search

    }

}