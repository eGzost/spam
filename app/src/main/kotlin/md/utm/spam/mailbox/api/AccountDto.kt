package md.utm.spam.mailbox.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AccountDto(
    val id: Long,
    val name: String,
    @SerialName("access_levels")
    val accessLevels: List<Int>
)
