package com.example.flipgame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.flipgame.viewmodel.MainViewModel
import com.example.flipgame.viewmodel.MainViewModel.Companion.NUMBER_OF_PAIRS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
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
        assertEquals(NUMBER_OF_PAIRS * 2, listViewModel.listNumbers.value?.size)
        listViewModel.previousClickPosition.value = -1
        listViewModel.increaseStep(1)
        assertEquals(1, listViewModel.previousClickPosition.value)
        listViewModel.increaseStep(5)
        assertEquals(true, listViewModel.listNumbers.value?.get(5)?.show)
    }
}
