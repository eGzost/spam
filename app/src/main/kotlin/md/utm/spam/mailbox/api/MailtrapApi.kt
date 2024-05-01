package md.utm.spam.mailbox.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class MailtrapApi(private val client: HttpClient) {

    suspend fun accounts(): List<AccountDto> = client.get("/accounts").body()

    suspend fun inboxes(accountId: Long): List<InboxDto> =
        client.get("/accounts/$accountId/inboxes").body()

    suspend fun messages(accountId: Long, inboxId: Long): List<MessageDto> =
        client.get("/accounts/${accountId}/inboxes/${inboxId}/messages").body()

    suspend fun body(accountId: Long, inboxId: Long, messageId: Long): String =
        client.get("/accounts/${accountId}/inboxes/${inboxId}/messages/$messageId/body.txt")
            .bodyAsText()
}