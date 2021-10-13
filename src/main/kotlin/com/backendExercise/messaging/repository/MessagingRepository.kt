package com.backendExercise.messaging.repository

import com.backendExercise.messaging.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessagingRepository : JpaRepository<Message, Long> {
    fun findAllByRecipient(recipient: String):List<Message>
}