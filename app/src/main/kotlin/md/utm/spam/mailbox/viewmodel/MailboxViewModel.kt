package md.utm.spam.mailbox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import md.utm.spam.classifier.Classifier
import md.utm.spam.classifier.Ham
import md.utm.spam.core.util.suspendForEach
import md.utm.spam.mailbox.model.Message
import md.utm.spam.mailbox.repository.EmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MailboxViewModel(
    private val repository: EmailRepository,
    private val classifier: Classifier
) : ViewModel() {

    private val mutableState = MutableStateFlow(MailboxState())
    val state: StateFlow<MailboxState>
        get() = mutableState

    private inline fun update(block: MailboxState.() -> MailboxState) = mutableState.update(block)

    init {
        load()
    }

    fun sort(by: EmailSort) = viewModelScope.launch {
        update {
            copy(
                messages = when (by) {
                    EmailSort.AtoZ -> messages.sortedBy { it.subject }
                    EmailSort.SpamFirst -> (spam.keys + ham.keys).toList()
                    EmailSort.HamFirst -> (ham.keys + spam.keys).toList()
                }
            )
        }
    }

    fun load() = viewModelScope.launch {
        update { copy(loading = true) }
        val inboxes = repository.inboxes()
        update {
            copy(
                inboxes = inboxes,
                messages = emptyList(),
                spam = emptyMap(),
                ham = emptyMap()
            )
        }
        inboxes.suspendForEach { inbox ->
            val messages = repository.messages(inbox)
            update {
                copy(
                    messages = messages,
                    loading = false
                )
            }
            messages.forEach(::processMessage)
        }
    }

    private fun processMessage(message: Message) = viewModelScope.launch {
        val result = classifier.check(message.subject + " " + message.body)
        update {
            if (result is Ham) {
                copy(ham = ham.toMutableMap().apply { this[message] = result.probability })
            } else {
                copy(spam = spam.toMutableMap().apply { this[message] = result.probability })
            }
        }
    }
}
