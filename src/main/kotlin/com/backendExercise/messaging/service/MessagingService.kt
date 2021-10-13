package com.backendExercise.messaging.service

import com.backendExercise.messaging.model.Message
import com.backendExercise.messaging.repository.MessagingRepository
import org.springframework.stereotype.Service

@Service
class MessagingService(
    private val messagingRepository: MessagingRepository
) {
    fun addNewMessage(message: Message) =
        messagingRepository.save(message)

    fun findAllMessagesByRecipient(recipient: String) =
        messagingRepository.findAllByRecipient(recipient)
}