package md.utm.spam.core.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

suspend inline fun <T, R> Iterable<T>.suspendMap(
    context: CoroutineContext = Dispatchers.Default,
    crossinline block: suspend (T) -> R
): List<R> = this.map {
    coroutineScope {
        async(context) {
            block(it)
        }
    }
}.awaitAll()

suspend inline fun <T, R> Iterable<T>.suspendForEach(
    context: CoroutineContext = Dispatchers.Default,
    crossinline block: suspend (T) -> R
) = this.forEach {
    coroutineScope {
        launch(context) {
            block(it)
        }
    }
}