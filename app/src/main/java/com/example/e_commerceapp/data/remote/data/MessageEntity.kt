package com.example.e_commerceapp.data.remote.data

import com.google.firebase.Timestamp

data class MessageEntity(
    val messageId:String = "",
    val senderId:String = "",
    val messageText : String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val type:String = "TEXT"
)
