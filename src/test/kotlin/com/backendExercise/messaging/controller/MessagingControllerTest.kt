package com.backendExercise.messaging.controller

import com.backendExercise.messaging.model.Message
import com.backendExercise.messaging.service.MessagingService
import com.nhaarman.mockitokotlin2.any
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers

import org.mockito.Mockito.`when` as upon


@WebMvcTest(MessagingController::class)
class MessagingControllerTest {

    private lateinit var mockServer: MockMvc

    @MockBean
    private lateinit var messagingService: MessagingService

    @BeforeEach
    fun setUp() {
        messagingService = mock(MessagingService::class.java)

        val messagingController = MessagingController(messagingService)

        mockServer = MockMvcBuilders.standaloneSetup(messagingController).build()
    }

    @Test
    fun `POST creates a new message`() {
        val message = Message(
            sender = "Moshe",
            recipient = "Gil",
            message = "Hi there!"
        )

        upon(messagingService.addNewMessage(any())).thenReturn(message)

        val messageJson = "{ \"sender\": \"Moshe\", \"recipient\": \"Gil\", \"message\": \"Hi there!\" }"
        mockServer.perform(
            MockMvcRequestBuilders.post("/messaging")
                .contentType(MediaType.APPLICATION_JSON)
                .content(messageJson)
                .header("Content-Type", "application/xml")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.sender", Matchers.equalTo("Moshe")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.recipient", Matchers.equalTo("Gil")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("Hi there!")))

        verify(messagingService).addNewMessage(message)
    }

    @Test
    fun `GET retrieve all messages of a specific recipient`() {
        val messages = listOf(
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

        upon(messagingService.findAllMessagesByRecipient("Gil")).thenReturn(messages)

        mockServer.perform(MockMvcRequestBuilders.get("/messaging?recipient=Gil"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.equalTo(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].sender", Matchers.equalTo("Moshe")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipient", Matchers.equalTo("Gil")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].message", Matchers.equalTo("Hi there!")))

        verify(messagingService).findAllMessagesByRecipient("Gil")
    }
}