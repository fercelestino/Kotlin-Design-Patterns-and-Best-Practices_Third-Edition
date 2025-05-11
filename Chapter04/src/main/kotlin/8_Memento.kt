fun main() {
    val michael = Manager()
    michael.think("Need to implement Coconut Cannon")
    michael.think("Should get some coffee")
    val memento = michael.saveThatThought()

    with(michael) {
        think("Or maybe tea?")
        think("No, actually, let's implement Pineapple Launcher")
    }

    //Minha implementacao alternativa - para entendimento - Tipo 1
//    fun receiverFunction(manager: Manager) {
//        manager.think("Or maybe tea?")
//        manager.think("No, actually, let's implement Pineapple Launcher")
//    }
//    with(michael, ::receiverFunction)

    //Minha implementacao alternativa - para entendimento - Tipo 2
//    val receiverFunction2 = { receiver:Manager ->
//        receiver.think("Or maybe tea?")
//        receiver.think("No, actually, let's implement Pineapple Launcher")
//    }
//    with(michael, receiverFunction2)

    //Minha implementacao alternativa - para entendimento - Tipo 3 - NÃO COMPILA
//    with(michael, { receiver:Manager ->
//        receiver.think("Or maybe tea?")
//        receiver.think("No, actually, let's implement Pineapple Launcher")
//    })

    michael.printThoughts()
    michael.recall(memento)
    michael.printThoughts()
}

class Manager {
    private var thoughts = mutableListOf<String>()

    fun printThoughts() {
        println(thoughts)
    }

    inner class Memory(private val mindState: List<String>) {
        fun restore() {
            thoughts = mindState.toMutableList()
        }
    }

    fun saveThatThought(): Memory {
        return Memory(thoughts.toList())
    }

    fun recall(memory: Memory) {
        memory.restore()
    }

    fun think(thought: String) {
        thoughts.add(thought)
        if (thoughts.size > 2) {
            thoughts.removeFirst()
        }
    }
}

