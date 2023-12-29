package task2

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


private const val N = 5
private const val NumberOfSteps = 10

class ChannelChain<T>(size: Int) {
    private val channels = (1..size)
        .toList()
        .map { _ ->
            Channel<T>()
        }

    fun getChannel(index: Int): Channel<T> = channels[index]
}

fun main(): Unit = runBlocking {
    val channelChains = (1..N)
        .toList()
        .map { _ ->
            ChannelChain<Int>(NumberOfSteps)
        }

    launch {
        produce(channelChains.map { chain -> chain.getChannel(0) })
    }
    (0 ..< NumberOfSteps - 1).forEach { i ->
        launch {
            process(
                channelsFrom = channelChains.map { chain -> chain.getChannel(i) },
                channelsTo = channelChains.map { chain -> chain.getChannel(i + 1)},
            )
        }
    }
    launch {
        consume(channelChains.map { chain -> chain.getChannel(NumberOfSteps - 1) })
    }
}

suspend fun produce(channels: List<Channel<Int>>) = coroutineScope {
    for (num in 1..N) {
        channels.forEach { channel ->
            launch {
                delay((0L..2000L).random())
                val producedNumber = (-NumberOfSteps + 1 .. -1).random()
                println("Produced: $producedNumber")
                channel.send(producedNumber)
            }
        }
    }
}

suspend fun consume(channels: List<Channel<Int>>) = coroutineScope {
    for (num in 1..N) {
        channels.forEach { receiveChannel ->
            launch {
                val number = receiveChannel.receive()
                println("<< Consumed: $number >>")
            }
        }
    }
}

suspend fun process(channelsFrom: List<Channel<Int>>, channelsTo: List<Channel<Int>>) = coroutineScope {
    for (num in 1..N) {
        channelsFrom.forEach { receiveChannel ->
            launch {
                val number = receiveChannel.receive()
                delay((0L..500L).random())
                channelsTo.random().send(number + 1)
            }
        }
    }
}
