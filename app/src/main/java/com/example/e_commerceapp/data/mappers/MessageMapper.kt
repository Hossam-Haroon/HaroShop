package com.example.e_commerceapp.data.mappers

import com.example.e_commerceapp.data.remote.data.MessageEntity
import com.example.e_commerceapp.domain.model.Message

fun MessageEntity.toDomain(): Message{
    return Message(
        messageId, senderId, messageText, timestamp, type
    )
}
fun Message.toEntity():MessageEntity{
    return MessageEntity(
        messageId, senderId, messageText, timestamp, type
    )
}

fun List<MessageEntity>.toDomain():List<Message> = this.map { it.toDomain() }
fun List<Message>.toEntity():List<MessageEntity> = this.map { it.toEntity() }