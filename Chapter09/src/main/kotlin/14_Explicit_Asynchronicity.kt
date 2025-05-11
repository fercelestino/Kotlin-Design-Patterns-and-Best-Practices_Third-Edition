import kotlinx.coroutines.*

fun main() {
    runBlocking {
        //this é o escopo - pode ser omitido
        // Prints DeferredCoroutine{Active}
        println("Result: ${this.getResult()}")

        // Prints "OK"
        println("Result: ${this.getResultAsync().await()}")
    }
}

// This will produce a warning
fun CoroutineScope.getResult(): Deferred<String> = async {
    delay(100)
    "OK"
}

fun CoroutineScope.getResultAsync() = async {
    delay(100)
    "OK"
}