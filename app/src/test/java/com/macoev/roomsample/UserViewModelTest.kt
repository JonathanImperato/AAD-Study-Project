package com.macoev.roomsample

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.macoev.roomsample.data.repository.FakeRepository
import com.macoev.roomsample.data.repository.Repository
import com.macoev.roomsample.data.User
import com.macoev.roomsample.viewmodel.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class UserViewModelTest {

    private lateinit var u: User
    private lateinit var userViewModel: UserViewModel
    private lateinit var repository: Repository
    private val context = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun setupViewModel() {
        FakeRepository().let {
            repository = it
            RepositoryLocator.repository = it
        }
        u = User.createRandom()
        repository.insert(u)
        userViewModel = UserViewModel(context)
    }

    @Test
    fun loadAllUser_dataLoaded() {
        val data = userViewModel.getAllUsers().value
        assertThat(data).isNotNull()
        assertThat(data).isNotEmpty()
        assertThat(data).contains(u)
    }

    @After
    fun removeAllUser_dataLoaded() = runBlockingTest {
        userViewModel.deleteAll()
    }
}