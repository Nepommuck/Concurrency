package task1

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


private const val N = 5

class ChannelPair<T> {
    val producerChannel = Channel<T>()
    val consumerChannel = Channel<T>()
}

fun main(): Unit = runBlocking {
    val channelPairs = (1..N)
        .toList()
        .map { _ ->
            ChannelPair<Int>()
        }

    for (pair in channelPairs) launch {
        forward(pair)
    }

    launch {
        produce(channelPairs.map { pair -> pair.producerChannel })
    }
    launch {
        consume(channelPairs.map { pair -> pair.consumerChannel })
    }
}

suspend fun produce(channels: List<Channel<Int>>) = coroutineScope {
    for (num in 1..N) {
        launch {
            channels.forEach { channel ->
                delay((0L..2000L).random())
                val producedNumber = (0..1000).random()
                println("Produced: $producedNumber")
                channel.send(producedNumber)
            }
        }
    }
}

suspend fun consume(channels: List<Channel<Int>>) = coroutineScope {
    for (num in 1..N) {
        launch {
            channels.forEach { receiveChannel ->
                val number = receiveChannel.receive()
                println("<< Consumed: $number >>")
            }
        }
    }
}

suspend fun forward(channels: ChannelPair<Int>) {
    while (true) {
        val portion = channels.producerChannel.receive()
        channels.consumerChannel.send(portion)
    }
}
