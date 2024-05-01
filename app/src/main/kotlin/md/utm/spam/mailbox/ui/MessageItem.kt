package md.utm.spam.mailbox.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import md.utm.spam.classifier.Classification
import md.utm.spam.classifier.Ham
import md.utm.spam.classifier.Spam
import md.utm.spam.mailbox.model.Message

@Composable
fun MessageItem(
    message: Message,
    classification: Classification?,
    modifier: Modifier = Modifier
) {
    var showBody by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.padding(horizontal = 15.dp, vertical = 10.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = classification,
                label = "Email item spam status",
                transitionSpec = { fadeIn() togetherWith fadeOut() }
            ) {
                when (it) {
                    null -> CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )

                    is Spam -> Icon(
                        Icons.Rounded.Clear,
                        contentDescription = "This message is spam",
                        tint = MaterialTheme.colorScheme.error
                    )

                    is Ham -> Icon(
                        Icons.Rounded.Check,
                        contentDescription = "This message is not spam",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Column {
                Text(
                    message.subject,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        message.from.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        message.from.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            IconButton(onClick = { showBody = !showBody }) {
                val rotation by animateFloatAsState(
                    targetValue = if (showBody) 180f else 0f,
                    label = ""
                )
                Icon(Icons.Rounded.KeyboardArrowUp, null, Modifier.rotate(rotation))
            }
        }

        AnimatedVisibility(visible = showBody) {
            Spacer(Modifier.height(15.dp))
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                HorizontalDivider()
                Text(message.body, style = MaterialTheme.typography.bodyMedium)
                classification?.probability?.let {
                    val text = "%.2f".format(it * 100) + "%"
                    Text(
                        text,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}