package com.inspekpro

import com.inspekpro.data.repository.FirestoreSyncRepository
import com.inspekpro.data.repository.InspectionSessionRepository
import com.inspekpro.receiver.AlarmScheduler
import com.inspekpro.ui.viewmodel.CreateSessionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Bagian Billy: Unit Testing Jadwal Inspeksi
 * Fitur: Pengujian logika validasi form tambah jadwal.
 * Tujuan: Memastikan bahwa sesi inspeksi hanya bisa dibuat jika data wajib (Judul, Lokasi, Inspektor) sudah terisi.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CreateSessionViewModelTest {

    private lateinit var viewModel: CreateSessionViewModel
    private val repository = mock(InspectionSessionRepository::class.java)
    private val alarmScheduler = mock(AlarmScheduler::class.java)
    private val firestoreSyncRepo = mock(FirestoreSyncRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = CreateSessionViewModel(repository, alarmScheduler, firestoreSyncRepo)
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
}
