package md.utm.spam.mailbox.repository

import md.utm.spam.mailbox.model.Inbox
import md.utm.spam.mailbox.model.Message

interface EmailRepository {
    suspend fun inboxes(): List<Inbox>
    suspend fun messages(inbox: Inbox): List<Message>
}