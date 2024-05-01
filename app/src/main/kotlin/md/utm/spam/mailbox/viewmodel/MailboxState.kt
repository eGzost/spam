package md.utm.spam.mailbox.viewmodel

import androidx.compose.runtime.Immutable
import md.utm.spam.mailbox.model.Inbox
import md.utm.spam.mailbox.model.Message

@Immutable
data class MailboxState(
    val loading: Boolean = false,
    val inboxes: List<Inbox> = emptyList(),
    val messages: List<Message> = emptyList(),
    val spam: Map<Message, Float> = emptyMap(),
    val ham: Map<Message, Float> = emptyMap(),
)