package com.backendExercise.messaging.service

import com.backendExercise.messaging.model.Message
import com.backendExercise.messaging.repository.MessagingRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when` as upon

@ExtendWith(MockitoExtension::class)
class MessagingServiceTest {
    @Mock
    private lateinit var messagingRepository: MessagingRepository

    @InjectMocks
    private lateinit var messagingService: MessagingService

    @Test
    fun `should find all messages that the recipient is Gil`() {
        val expectedMessages = listOf(
            Message(
                sender = "Moshe",
                recipient = "Gil",
                message = "Hi there!"
            ),
            Message(
                sender = "Yossi",
                recipient = "Gil",
                message = "Hi Gil"
            )
        )

        upon(messagingRepository.findAllByRecipient("Gil"))
            .thenReturn(expectedMessages)

        val actualMessages = messagingService.findAllMessagesByRecipient("Gil")

        assertEquals(expectedMessages, actualMessages)
    }

    @Test
    fun `should add new message and retrieve it`() {
        val expectedMessage =
            Message(
                sender = "Moshe",
                recipient = "Gil",
                message = "Hi there!"
            )

        upon(messagingRepository.save(expectedMessage))
            .thenReturn(expectedMessage)

        val actualMessage = messagingService.addNewMessage(expectedMessage)

        assertEquals(expectedMessage, actualMessage)
    }
}