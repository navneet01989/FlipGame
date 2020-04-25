package com.example.flipgame.viewmodel

import android.os.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flipgame.model.Number

class MainViewModel: ViewModel() {
    companion object{
        const val NUMBER_OF_PAIRS = 10
    }
    val listNumbers = MutableLiveData<List<Number>>()
    val pairsDone by lazy { MutableLiveData(0) }
    val isCardPendingToClose by lazy { MutableLiveData(false) }
    val step by lazy { MutableLiveData(0) }
    val previousClickPosition by lazy { MutableLiveData(-1) }
    val companionClickPosition by lazy { MutableLiveData(-1) }

    fun getNumberOfPairs() = NUMBER_OF_PAIRS

    fun generateNumbers() {
        val array = arrayListOf<Number>()
        for(x in 1..(getNumberOfPairs())) {
            array.add(Number(x, false))
            array.add(Number(x, false))
        }
        array.shuffle()
        listNumbers.value = array
        step.value = 0
    }

    fun increaseStep(position: Int) {
        listNumbers.value?.let { list ->
            step.value?.let {
                step.value = it + 1
            }
            when {
                previousClickPosition.value!! == -1 -> {
                    list[position].show = true
                    previousClickPosition.value = position
                }
                list[previousClickPosition.value!!].number == list[position].number -> {
                    list[position].show = true
                    previousClickPosition.value = -1
                    pairsDone.value?.let {
                        pairsDone.value = it + 1
                    }
                }
                else -> {
                    list[position].show = true
                    isCardPendingToClose.value = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        list[previousClickPosition.value!!].show = false
                        list[position].show = false
                        companionClickPosition.value = position
                        isCardPendingToClose.value = false
                        previousClickPosition.value = -1
                    }, 1000)
                }
            }
        }
    }
}
