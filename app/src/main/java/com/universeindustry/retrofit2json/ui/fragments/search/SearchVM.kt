package com.universeindustry.retrofit2json.ui.fragments.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchVM : ViewModel(){
    /*
    Disposable :
        Disposable은 Observer가 Observable을 구독할 때 발행되는 객체이다.
        또한 Disposable의 인터페이스인 dispose()를 사용함으로써 구독을 해제해 메모리 누수를 방지할 수 있다.
        CompositeDisposable을 사용하여 모든 Disposable 구독을 ViewModel에서 해제해준다.

    ViewModel 사용시 주의사항 :
        - 링크 : https://blog.gangnamunni.com/post/mvvm_anti_pattern/
        - viewmodel에는 최대한 android framework가 최대한 없는 방향으로 코드를 작성해야한다.
        - 메모리 leak 유발하는 요소
            1. activity의 class(context 참조하는 경우), view, context는 lifecycle 길이상 맞지 않는다.
        - view를 참조하고 싶을때는 LiveData observing을 이용하여 view를 update한다.

        - context를 사용하시 viewmodel의 라이프 사이클이 더 길어서 메모리 누수가 생긴다. 라이프 사이클 길이 대조해보면 앞뒤가 안맞음.
        - CompositeDisposable을 사용하여 UseCase에서 발행된 Disposable을 관리한다.
        - Activity/Fragment에 if나 for등 복잡한 로직이 존재하면 안된다.
          ViewModel 안에 아니면 다른 layer에 존재해야한다. 최소한의 로직만 있어야한다.
        - 여러개의 ViewModel보다는 단 하나의 VM만 사용하는 것을 권장한다.
          부득이하게 여러개의 VM을 사용해아 할 경우, 여러개의 LiveData를 가지는게 더 효율적이다.
        - 하나의 VM에는 여러개의 view를 가질 수 있지만, 여러개의 view의 로직들이 하나의 VM에 존재한다면
          VM안의 코드들이 길어지거나, 많은 responsibilities를 가지게된다.
          일부 로직들을 VM과 동일한 범위의 Presenter Layer로 이동시키면 된다.
          예) VM의 생성자로 context를 받아서 sharedPrefeerence를 사용하여 데이터를 저장 -> X
              VM의 생성자로 "Repositoy"를 받고, "Repositoy"에서 SharedPreference관련된 작업을 진행한다. (Repositoy 생성자에서 context사용.)


    MediatorLiveData :
     - 개념
        1) 여러 개의 LiveData를 관찰할 수 있으며 MediatorLiveData 자신 또한 관찰의 대상이 되어야 정상적으로 동작.
     - 링크
        1) https://velog.io/@hkg5600/%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%8F%84%EC%84%9C-%EA%B2%80%EC%83%89-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%95%B1-%EB%A7%8C%EB%93%A4%EA%B8%B0-3%ED%8E%B8-UseCase%EC%99%80-ViewModel

    RecyclerView adapter xml :
     - 링크
        1) https://velog.io/@hkg5600/%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%8F%84%EC%84%9C-%EA%B2%80%EC%83%89-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%95%B1-%EB%A7%8C%EB%93%A4%EA%B8%B0-4%ED%8E%B8-%EB%81%9D

    postValue vs value
        Livedata.postValue("a")
            - 백그라운드에 진행.
            - postValue 이후에 바로 value를 호출할 시, 변경된 값을 받아오지 못할 가능성이 크다.
                반면, value로 값을 변경하면 메인쓰레드에서 변경하는 것이기 떄문에 바로 다음 라인에서 value로 변경된 값을 읽어올 수 있다.

        Livedata.value("b")
            - 메인 쓰레드에서 그 즉시 값이 반영된다(메인 쓰레드에서 값을 dispatch 시킨다.)
     */

    val _currentText = MutableLiveData<String>()

    // mutable과 non-mutable의 차이는 타입의 변화 차이이다.
    private val _list = MutableLiveData<ArrayList<Any>>()
    val searchList : LiveData<ArrayList<Any>>
        get() = _list


    //초기 값 설정
    init {
        _currentText.value = ""
        _list.value = arrayListOf()
    }

    /*
    ViewModel 종료시에 호출된다.
    해당 구간에서 모든 Disposable의 구독을 해제한다.
     */
    override fun onCleared() {
        super.onCleared()
        try {
            _currentText.value = ""
            _list.value = arrayListOf()
        }catch (e:Exception){
            Log.e("mException", "SearchVM, onCleared // Exception : ${e.message}")
        }
    }
}