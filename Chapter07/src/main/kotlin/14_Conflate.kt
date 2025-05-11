import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val stock: Flow<Int> = flow {
            var i = 0
            while (true) {
                emit(++i)
                delay(400)
            }
        }

        var seconds = 0

        //conflate - descarta valores gerados entre os collets - então emite sempre o valor mais recente a cada vez que o consumer pede um valor
        //portanto seu ritmo de emissão está ligado à velocidade do consumer em processar cada item.
        //Ele não sobre backpresure, continua gerando valores, e emite o mais recente toda vez que o consumer executa uma iteração do collect.
//        val specialFlow = stock.conflate()


        //Debounce - é para rate limit - só emite um valor para o consumer quando seu algoritmo para de gerar continuamente novos valores em
        //intervaloe menores do que o timeout estipulado. Ele poupa o consumer de coletar muito rápido.
        //Se o seu algoritmo eternamente gerar novos valores em intervalo menor que o timeout estipulado, o collector nunca recebe valor nenhum.
//        val specialFlow = stock.debounce(300L)

        //Sample - Independente da velocidade do collector em processar, a cada vez que passa o seu timeout interno esse se torna o valor da vez a ser coletado
        //pula de propósito valores emitidos pelo seu algoritmo dentro do período estipulado.
        val specialFlow = stock.sample(300L)

        specialFlow.collect { number ->
            delay(1000)
            seconds++
            println("$seconds seconds -> received $number")
        }

    }
}