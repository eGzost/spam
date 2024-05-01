package md.utm.spam.mailbox.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import md.utm.spam.classifier.Ham
import md.utm.spam.classifier.Spam
import md.utm.spam.mailbox.viewmodel.MailboxState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageList(state: MailboxState, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = spacedBy(10.dp),
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
        items(
            state.messages,
            key = { it.id }
        ) { message ->
            MessageItem(
                message = message,
                classification = when (message) {
                    in state.spam.keys -> Spam(state.spam[message]!!)
                    in state.ham.keys -> Ham(state.ham[message]!!)
                    else -> null
                },
                modifier = Modifier
                    .animateItemPlacement()
                    .shadow(5.dp, shapes.extraSmall)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        colorScheme.surfaceContainer,
                        shapes.extraSmall
                    )
            )
        }
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}
