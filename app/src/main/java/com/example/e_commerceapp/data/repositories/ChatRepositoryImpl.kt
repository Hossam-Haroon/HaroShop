package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.core.Utils.MESSAGE_COLLECTION
import com.example.e_commerceapp.core.Utils.TICKET_COLLECTION
import com.example.e_commerceapp.core.Utils.TIME_STAMP
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.data.mappers.toEntity
import com.example.e_commerceapp.data.remote.data.MessageEntity
import com.example.e_commerceapp.domain.model.Message
import com.example.e_commerceapp.domain.repositories.ChatRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
):ChatRepository {
    override fun getMessages(ticketId: String): Flow<List<Message>> {
        return callbackFlow {
            val listener = firestore.collection(TICKET_COLLECTION).document(ticketId)
                .collection(MESSAGE_COLLECTION)
                .orderBy(TIME_STAMP, Query.Direction.ASCENDING)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val messages = snapShot?.toObjects(
                        MessageEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(messages)
                }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun sendMessage(message: Message, ticketId: String) {
        val ticketRef = firestore.collection(TICKET_COLLECTION).document(ticketId)
        val messageRef = ticketRef.collection(MESSAGE_COLLECTION)
        val newMessageRef = messageRef.document()
        val messageId = newMessageRef.id
        val messageEntity = message.copy(messageId = messageId).toEntity()
        newMessageRef.set(messageEntity).await()
    }
}