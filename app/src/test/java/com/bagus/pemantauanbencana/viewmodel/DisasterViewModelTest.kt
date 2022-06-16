package com.bagus.pemantauanbencana.viewmodel

import android.database.Observable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.source.local.UserEntity
import com.bagus.pemantauanbencana.source.remote.DisasterRepository
import com.bagus.pemantauanbencana.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DisasterViewModelTest {

    private lateinit var viewModel: DisasterViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var disasterRepository: DisasterRepository

    @Mock
    private lateinit var disasterObserver: Observer<List<DisasterEntity>>

    @Mock
    private lateinit var userObserver: Observer<UserEntity>

    @Before
    fun setUp(){
        viewModel = DisasterViewModel(disasterRepository)
    }

    @Test
    fun getAllDisaster() {
        val dummyDisaster = DataDummy.generateDummyDisaster()
        val disaster = MutableLiveData<List<DisasterEntity>>()
        disaster.value = dummyDisaster

        `when`(disasterRepository.getAllDisaster()).thenReturn(disaster)
        val disasterEntities = viewModel.getAllDisaster().value
        verify(disasterRepository).getAllDisaster()
        assertNotNull(disasterEntities)
        assertEquals(1, disasterEntities?.size)

        viewModel.getAllDisaster().observeForever(disasterObserver)
        verify(disasterObserver).onChanged(dummyDisaster)
    }

    @Test
    fun getUserDetail() {
        val dummyUser = DataDummy.generateDummyUser()
        val user = MutableLiveData<UserEntity>()
        user.value = dummyUser

        `when`(disasterRepository.getUserData("username","password")).thenReturn(user)
        val userEntities = viewModel.getUserDetail("username","password").value
        verify(disasterRepository).getUserData("username","password")
        assertNotNull(userEntities)
        assertEquals("firstname", userEntities?.firstName)
        assertEquals("lastname", userEntities?.lastName)
        assertEquals("avatar", userEntities?.avatar)
        assertEquals("email@gmail.com", userEntities?.email)

        viewModel.getUserDetail("username","password").observeForever(userObserver)
        verify(userObserver).onChanged(dummyUser)
    }
}