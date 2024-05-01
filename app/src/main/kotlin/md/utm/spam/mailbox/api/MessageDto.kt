package md.utm.spam.mailbox.api


import md.utm.spam.mailbox.model.Message
import md.utm.spam.mailbox.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MessageDto(
    val id: Long,
    @SerialName("inbox_id")
    val inboxId: Int,
    val subject: String,
    @SerialName("sent_at")
    val sentAt: String,
    @SerialName("from_email")
    val fromEmail: String,
    @SerialName("from_name")
    val fromName: String,
    @SerialName("to_email")
    val toEmail: String,
    @SerialName("to_name")
    val toName: String,
    @SerialName("email_size")
    val emailSize: Int,
    @SerialName("is_read")
    val isRead: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("html_body_size")
    val htmlBodySize: Int,
    @SerialName("text_body_size")
    val textBodySize: Int,
    @SerialName("human_size")
    val humanSize: String,
    @SerialName("html_path")
    val htmlPath: String,
    @SerialName("txt_path")
    val txtPath: String,
    @SerialName("raw_path")
    val rawPath: String,
    @SerialName("download_path")
    val downloadPath: String,
    @SerialName("html_source_path")
    val htmlSourcePath: String,
) {
    @Serializable
    class BlacklistsReportInfo(
        val result: String,
        val domain: String,
        val ip: String,
        val report: List<Report>
    ) {
        @Serializable
        class Report(
            val name: String,
            val url: String,
            @SerialName("in_black_list")
            val inBlackList: Boolean
        )
    }

    @Serializable
    class SmtpInformation(
        val ok: Boolean
    )
}

fun MessageDto.toDomain(body: String) = Message(
    id = id,
    from = User(
        address = fromEmail,
        name = fromName
    ),
    subject = subject,
    body = body
)
