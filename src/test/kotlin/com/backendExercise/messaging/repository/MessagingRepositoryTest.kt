package com.backendExercise.messaging.repository

import com.backendExercise.messaging.model.Message
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessagingRepositoryTest {
    @Autowired
    private lateinit var messagingRepository: MessagingRepository

    @BeforeEach
    fun setUp() {
        cleanUp()

        messagingRepository.saveAll(
            listOf(
                Message(
                    sender = "Moshe",
                    recipient = "Gil",
                    message = "Hi there!"
                ),
                Message(
                    sender = "Yossi",
                    recipient = "Gil",
                    message = "Hi Gil"
                ),
                Message(
                    sender = "Gil",
                    recipient = "Moshe",
                    message = "Hi, How are you?"
                )
            )
        )
    }

    @AfterEach
    fun cleanUp() {
        messagingRepository.deleteAll()
    }

    @Test
    fun `Should retrieve all messages that the recipient is Gil`() {
        val actual = messagingRepository.findAllByRecipient(
            recipient = "Gil"
        )

        assertEquals(2, actual.size)
    }

    @Test
    fun `Should not retrieve any message when the recipient is not exist`() {
        val actual = messagingRepository.findAllByRecipient(
            recipient = "Yossi"
        )

        assertEquals(0, actual.size)
    }
}