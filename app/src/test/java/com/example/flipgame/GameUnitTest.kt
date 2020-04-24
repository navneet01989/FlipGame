package com.example.flipgame

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.flipgame.model.Number
import com.example.flipgame.view.MainFragment
import com.example.flipgame.viewmodel.MainViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GameUnitTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()
    var listViewModel = MainViewModel()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun getNumberGenerated() {
        listViewModel.generateNumbers()
        assertEquals(listViewModel.NUMBER_OF_PAIRS * 2, listViewModel.listNumbers.value?.size)
    }
}
