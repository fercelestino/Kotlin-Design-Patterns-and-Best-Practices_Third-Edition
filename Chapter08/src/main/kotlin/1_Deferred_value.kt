import kotlinx.coroutines.*
import kotlin.random.Random

fun main() {
    runBlocking {
        val value = valueAsync2()
        println(value.await())
    }
}

suspend fun valueAsync(): Deferred<String> = coroutineScope {
    val deferred = CompletableDeferred<String>()
    launch {
        delay(100)
        if (Random.nextBoolean()) {
            deferred.complete("OK")
        } else {
            deferred.completeExceptionally(
                RuntimeException()
            )
        }
    }
    deferred
}

//Implementação equivalente à acima
fun valueAsync2(): Deferred<String> {
    val scope = CoroutineScope(Dispatchers.Default)
    return scope.async {
        delay(100)
        if (Random.nextBoolean()) {
            "OK"
        } else {
            throw RuntimeException()
        }
    }
}