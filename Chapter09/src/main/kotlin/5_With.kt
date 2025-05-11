fun main() {
    val actor = with(JamesBondMovie()) {
        actorName = "Pierce Brosnan"
        println(this.actorName)
        this
    }.actorName

    println("outside $actor")
}