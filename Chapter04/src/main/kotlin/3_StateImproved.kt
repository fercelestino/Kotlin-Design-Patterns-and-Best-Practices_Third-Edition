fun main() {
    val snail = ISnail()
    println(snail)
    snail.seeHero()
    println(snail)
    snail.getHit(1)
    println(snail)
    snail.getHit(10)
    println(snail)
}

interface IWhatCanHappen {
    fun seeHero()

    fun getHit(pointsOfDamage: Int)

    fun calmAgain()
}

class ISnail: IWhatCanHappen {
    internal var healthPoints = 10
    internal var currentMood: IMood = IStill(this)
    override fun seeHero() {
        currentMood.seeHero()
    }

    override fun getHit(pointsOfDamage: Int) {
        currentMood.getHit(pointsOfDamage)
    }

    override fun calmAgain() {
        currentMood.calmAgain()
    }


    override fun toString(): String {
        return "Snail {health: $healthPoints, mood: ${currentMood::class.simpleName}}"
    }
}

sealed class IMood(private val context: ISnail): IWhatCanHappen {
    override fun getHit(pointsOfDamage: Int) {
        context.healthPoints -= pointsOfDamage
        if (context.healthPoints <= 0) {
            context.currentMood = IDead(context)
        }
    }
}


data class IStill(private val context: ISnail): IMood(context) {
    override fun seeHero() {
        context.currentMood = IAgressive(context)
    }

    override fun calmAgain() {
        //as you were
    }

}

data class IAgressive(private val context: ISnail): IMood(context) {
    override fun seeHero() {
        //keep goint
    }

    override fun getHit(pointsOfDamage: Int) {
        super.getHit(pointsOfDamage)
        if (context.currentMood !is IDead) {
            context.currentMood = IRetreating(context)
        }
    }

    override fun calmAgain() {
        context.currentMood = IStill(context)
    }

}

data class IRetreating(private val context: ISnail): IMood(context) {
    override fun seeHero() {
        //keep retreating
    }

    override fun calmAgain() {
        context.currentMood = IStill(context)
    }

}

data class IDead(private val context: ISnail): IMood(context) {
    override fun seeHero() {

    }

    override fun calmAgain() {

    }

}

