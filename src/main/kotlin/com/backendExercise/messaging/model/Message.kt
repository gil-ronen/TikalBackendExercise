package com.backendExercise.messaging.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "sender")
    val sender: String,

    @Column(name = "recipient")
    val recipient: String,

    @Column(name = "message")
    val message: String
)