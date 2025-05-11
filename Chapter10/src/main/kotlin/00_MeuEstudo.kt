import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import arrow.core.right

sealed interface Error
data object MyNoSpaceInBox : Error
data class MyNoSuchDonut(val name: String) : Error
data class UnexpectedError(val message: String) : Error

class MyDonutBoxEither(private val capacity: Int) {
    private val donuts = mutableListOf<Donut>()

//    fun addDonut(donut: Donut): Either<MyNoSpaceInBox, MyDonutBoxEither> =
//        if (donuts.size < capacity) {
//            donuts.add(donut)
//            this.right()
//        } else {
//            MyNoSpaceInBox.left()
//        }

    fun addDonut(donut: Donut): Either<MyNoSpaceInBox, MyDonutBoxEither> =
        either {
            ensure(donuts.size < capacity) {
                MyNoSpaceInBox
            }
            donuts.add(donut)
            this@MyDonutBoxEither
        }

    fun removeDonut(name: String):Either<MyNoSuchDonut, Donut> =
        either {
            val donutToRemove = donuts.find { it.name == name }
            ensureNotNull(donutToRemove) { MyNoSuchDonut(name) }
            donuts.remove(donutToRemove)
            donutToRemove
        }
}

    fun usoVerbosoTradicional() {
        val donutBox = MyDonutBoxEither(1)
        val result = donutBox.addDonut(Donut("brigadeiro", 100, listOf("whey")))
        val resultMessage = when (result) {
            is Either.Left -> "falha"
            is Either.Right -> "sucesso"
        }
        println("Fase 1 result: $resultMessage")

        val result2 = donutBox.addDonut(Donut("tradicional", 100, listOf()))
        val resultMessage2 = when (result2) {
            is Either.Left -> "falha"
            is Either.Right -> "sucesso"
        }
        println("Fase 2 result: $resultMessage2")

        val resultRemove1 = donutBox.removeDonut("brigadeiro")
        val resultMessageRemo1 = when (resultRemove1) {
            is Either.Left -> "falha"
            is Either.Right -> "sucesso"
        }
        println("Remocao fase 1 result: $resultMessageRemo1")

        val resultRemove2 = donutBox.removeDonut("tradicional")
        val resultMessageRemo2 = when (resultRemove2) {
            is Either.Left -> "falha: ${resultRemove2.value.name}"
            is Either.Right -> "sucesso"
        }
        println("Remocaob fase 2 result: $resultMessageRemo2")
    }

    fun umaFuncaoQueLancaExcecao(valor: Int) {
        require(valor > 0) { "Valor deve ser positivo " }
        println("ok")
    }

    fun umaFuncaoQueChamaOutrasComEither(): Either<Error, Donut> = either {
        Either.catch {
            val donutBox = MyDonutBoxEither(1)
            donutBox.addDonut(Donut("brigadeiro", 100, listOf("whey"))).bind()
            //donutBox.addDonut(Donut("tradicional", 100, listOf())).bind()
//            umaFuncaoQueLancaExcecao(-1)
            umaFuncaoQueLancaExcecao(1)
            donutBox.removeDonut("brigadeiro").bind()
            //donutBox.removeDonut("tradicional").bind()
        }.mapLeft {
            UnexpectedError(it.message ?: "Unknown error")
        }.bind()

    }

    fun usoCompacto() {

        val result = umaFuncaoQueChamaOutrasComEither()
        when (result) {
            is Either.Left -> {
                val error = result.value
                when (error) {
                    is MyNoSpaceInBox -> println("no space in box")
                    is MyNoSuchDonut -> println("donut ${error.name} not found")
                    is UnexpectedError -> println("Deu pau: ${error.message}")
                }
            }

            is Either.Right -> println("removido donut ${result.value.name}")
        }
    }


    fun main() {
        //usoVerbosoTradicional()
        usoCompacto()
    }