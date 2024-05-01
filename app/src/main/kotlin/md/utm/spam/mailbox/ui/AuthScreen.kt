package md.utm.spam.mailbox.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import md.utm.spam.core.cache.Cache

@Composable
fun AuthScreen(onLogin: () -> Unit, cache: Cache) {

    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(
                bottom = WindowInsets.ime
                    .asPaddingValues()
                    .calculateBottomPadding()
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.large)
                .padding(horizontal = 20.dp, vertical = 15.dp)
        ) {
            Text(
                "Enter Mailtrap API key",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier)

            val (key, setKey) = remember {
                mutableStateOf("")
            }
            OutlinedTextField(
                value = key,
                onValueChange = setKey,
                placeholder = { Text("API key") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        cache.saveKey(key)
                        onLogin()
                    }
                ),
                leadingIcon = { Icon(Icons.Rounded.Key, null) },
                trailingIcon = {

                    val image = if (showPassword)
                        Icons.Rounded.Visibility
                    else Icons.Rounded.VisibilityOff

                    IconButton(onClick = { showPassword = !showPassword }) {
                        AnimatedContent(
                            targetState = image,
                            label = "",
                            transitionSpec = { scaleIn() togetherWith fadeOut() }
                        ) {
                            Icon(imageVector = it, null)
                        }
                    }
                }
            )

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        cache.saveKey(key)
                        onLogin()
                    },
                    enabled = key.isNotEmpty()
                ) {
                    Text("Login")
                }
            }
        }
    }
}