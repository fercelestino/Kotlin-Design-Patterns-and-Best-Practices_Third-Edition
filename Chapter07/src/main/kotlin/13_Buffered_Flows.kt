import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {
        val numbersFlow: Flow<Int> = flow {
            println("New subscriber!")
            (1..10).forEach {
                println("Sending $it")
                delay(1000)
                emit(it)
            }
        }

        (1..4).forEach { coroutineId ->
            launch {
                delay(1000)
                numbersFlow.buffer().collect { number ->
                    //delay(1000)
                    println("Coroutine $coroutineId received $number")
                }
            }
        }
    }
}