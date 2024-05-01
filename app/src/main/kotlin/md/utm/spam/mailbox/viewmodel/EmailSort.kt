package md.utm.spam.mailbox.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.SortByAlpha

enum class EmailSort {
    AtoZ, SpamFirst, HamFirst
}

val EmailSort.title
    get() = when (this) {
        EmailSort.AtoZ -> "A-Z"
        EmailSort.SpamFirst -> "Spam first"
        EmailSort.HamFirst -> "Ham first"
    }
val EmailSort.icon
    get() = when (this) {
        EmailSort.AtoZ -> Icons.Rounded.SortByAlpha
        EmailSort.SpamFirst -> Icons.Rounded.Clear
        EmailSort.HamFirst -> Icons.Rounded.Check
    }