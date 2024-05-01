package md.utm.spam.mailbox.model

data class Message(
    val id: Long,
    val from: User,
    val subject: String,
    val body: String,
)
