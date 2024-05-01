package md.utm.spam.mailbox.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import md.utm.spam.mailbox.viewmodel.MailboxViewModel

@Composable
fun MailboxScreen(viewModel: MailboxViewModel, modifier: Modifier = Modifier) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    AnimatedContent(targetState = state.loading, label = "") {
        if (it) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    strokeCap = StrokeCap.Round
                )
            }
        } else {
            MessageList(
                state = state,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .then(modifier)
            )
        }
    }
}