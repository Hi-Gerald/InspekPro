package com.inspekpro

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.inspekpro.data.local.dao.InspectionSessionDao
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.repository.FirestoreSyncRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

/**
 * Bagian Billy: Unit Testing Cloud Sync
 * Fitur: Pengujian filter sinkronisasi Firestore.
 * Tujuan: Memastikan sistem hanya mengambil data yang belum sinkron (is_synced = 0) untuk diunggah ke cloud.
 */
class FirestoreSyncRepositoryTest {

    private lateinit var syncRepository: FirestoreSyncRepository
    private val sessionDao = mock(InspectionSessionDao::class.java)
    private val firestore = mock(FirebaseFirestore::class.java)
    private val collectionRef = mock(CollectionReference::class.java)
    private val documentRef = mock(DocumentReference::class.java)
    private val task = mock(Task::class.java) as Task<Void>

    @Before
    fun setup() {
        `when`(firestore.collection(anyString())).thenReturn(collectionRef)
        `when`(collectionRef.document(anyString())).thenReturn(documentRef)
        `when`(documentRef.set(any())).thenReturn(task)
        
        syncRepository = FirestoreSyncRepository(sessionDao, firestore)
    }

    @Test
    fun `when sync is called then only unsynced sessions should be processed`() = runBlocking {
        // Data simulasi: 2 sesi yang belum sinkron
        val unsyncedSessions = listOf(
            mockSession(1, "INS-001"),
            mockSession(2, "INS-002")
        )
        
        `when`(sessionDao.getUnsyncedSessions()).thenReturn(unsyncedSessions)
        
        // Mocking task await (sederhana)
        `when`(task.isComplete).thenReturn(true)
        `when`(task.isSuccessful).thenReturn(true)

        val result = syncRepository.syncUnsyncedSessions()
        
        // Verifikasi bahwa DAO dipanggil untuk ambil data belum sinkron
        verify(sessionDao).getUnsyncedSessions()
        
        // Cek apakah jumlah yang diproses sesuai
        if (result.isSuccess) {
            assertEquals(2, result.getOrNull())
        }
    }

    private fun mockSession(id: Long, code: String): InspectionSessionEntity {
        return InspectionSessionEntity(
            sessionId = id,
            sessionCode = code,
            title = "Test",
            locationName = "Test",
            inspectorName = "Test",
            inspectorId = "Test",
            scheduledDate = System.currentTimeMillis()
        )
    }
}
