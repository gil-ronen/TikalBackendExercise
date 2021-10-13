package com.backendExercise.messaging.controller

import com.backendExercise.messaging.model.Message
import com.backendExercise.messaging.service.MessagingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messaging")
class MessagingController (
    private val messagingService: MessagingService
) {
    @PostMapping
    fun sendMessage(@RequestBody message: Message): ResponseEntity<Message> =
        ResponseEntity<Message>(messagingService.addNewMessage(message), HttpStatus.OK)

    @GetMapping
    fun receiveMessages(@RequestParam("recipient") recipient: String): ResponseEntity<List<Message>> =
        ResponseEntity<List<Message>>(messagingService.findAllMessagesByRecipient(recipient), HttpStatus.OK)
}