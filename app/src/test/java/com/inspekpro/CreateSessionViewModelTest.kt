package com.inspekpro

import com.inspekpro.data.repository.FirestoreSyncRepository
import com.inspekpro.data.repository.InspectionSessionRepository
import com.inspekpro.receiver.AlarmScheduler
import com.inspekpro.ui.viewmodel.CreateSessionViewModel
import com.inspekpro.ui.viewmodel.CreateSessionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Bagian Billy: Unit Testing Jadwal Inspeksi
 * Fitur: Pengujian logika validasi form, fungsi reset, dan validasi waktu.
 * Tujuan: Memastikan kualitas fitur Billy terjaga dan tidak terjadi bug pada logika bisnis utama.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CreateSessionViewModelTest {

    private lateinit var viewModel: CreateSessionViewModel
    private val repository = mock(InspectionSessionRepository::class.java)
    private val alarmScheduler = mock(AlarmScheduler::class.java)
    private val firestoreSyncRepo = mock(FirestoreSyncRepository::class.java)
    private val findingRepo = mock(com.inspekpro.data.repository.FindingRepository::class.java)
    private val authRepo = mock(com.inspekpro.data.repository.AuthRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = CreateSessionViewModel(repository, alarmScheduler, firestoreSyncRepo, findingRepo, authRepo)
    }

    @Test
    fun `when fields are empty then isFormValid should be false`() = runBlocking {
        viewModel.title.value = ""
        viewModel.locationName.value = ""
        viewModel.inspectorName.value = ""
        
        val isValid = viewModel.isFormValid.first()
        assertFalse(isValid)
    }

    @Test
    fun `when fields are filled then isFormValid should be true`() = runBlocking {
        viewModel.title.value = "Turbine Check"
        viewModel.locationName.value = "Plant A"
        viewModel.inspectorName.value = "Sofia"
        
        val isValid = viewModel.isFormValid.first()
        assertTrue(isValid)
    }

    @Test
    fun `when date is in the past then createSession should fail with error`() = runBlocking {
        // Set date to 24 hours ago
        viewModel.scheduledDate.value = System.currentTimeMillis() - 86400000
        viewModel.title.value = "Object Test"
        viewModel.locationName.value = "Loc Test"
        viewModel.inspectorName.value = "Billy"
        
        viewModel.createSession("USER-001", com.inspekpro.data.local.entity.SessionStatus.DRAFT)
        
        val result = viewModel.createResult.value
        assertTrue(result is CreateSessionResult.Error)
        assertEquals("Waktu inspeksi harus di masa depan", (result as CreateSessionResult.Error).message)
    }

    @Test
    fun `when resetForm is called then all states should return to default`() = runBlocking {
        viewModel.title.value = "Title"
        viewModel.locationName.value = "Location"
        viewModel.inspectorName.value = "Inspector"
        viewModel.videoPath.value = "path/to/video"
        
        viewModel.resetForm()
        
        assertEquals("", viewModel.title.value)
        assertEquals("", viewModel.locationName.value)
        assertEquals("", viewModel.inspectorName.value)
        assertNull(viewModel.videoPath.value)
        assertTrue(viewModel.createResult.value is CreateSessionResult.Idle)
    }
}
