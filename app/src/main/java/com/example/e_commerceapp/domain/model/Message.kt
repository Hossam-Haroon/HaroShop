package com.example.e_commerceapp.domain.model

import com.google.firebase.Timestamp

data class Message(
    val messageId:String ,
    val senderId:String ,
    val messageText : String ,
    val timestamp: Timestamp ,
    val type:String
)
