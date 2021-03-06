package com.macoev.aadstudyproject

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.repository.FakeRepository
import com.macoev.aadstudyproject.data.repository.Repository
import com.macoev.aadstudyproject.viewmodel.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.KoinApiExtension
import org.robolectric.RobolectricTestRunner

@KoinApiExtension
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class UserViewModelTest {

    private lateinit var u: User
    private lateinit var userViewModel: UserViewModel
    private lateinit var repository: Repository<User>
    private val context = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun setupViewModel() {
        FakeRepository().let {
            repository = it
            RepositoryLocator.userRepository = it
        }
        u = User.createRandom()
        repository.insert(u)
        userViewModel = UserViewModel(context)
    }

    @Test
    fun loadAllUser_dataLoaded() {
        val data = userViewModel.getAll().value
        assertThat(data).isNotNull()
        assertThat(data).isNotEmpty()
        assertThat(data).contains(u)
    }

    @After
    fun removeAllUser() = runBlockingTest {
        userViewModel.deleteAll()
    }
}