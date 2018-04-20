package sandbox

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking


fun main(args: Array<String>)  {

    val deferred = (1..1_000_000).map { n ->
        async {
            workload(n)
        }
    }

    runBlocking {
        val sum = deferred.sumBy { it.await() }
        println("Sum: $sum")
    }

}

// takes days
private suspend fun synchronousSum(): Int {
    val sum = (1..1_000_000).sumBy { n ->
        delay(1000)
        n
    }
    return sum
}


suspend fun workload(n: Int): Int {
    delay(1000)
    return n
}
