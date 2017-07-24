package c6h2cl2.discordbot.wakaryu

import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.impl.obj.User

/**
 * @author C6H2Cl2
 */

const val OWNER_ID = 217261423041052672L

const val COBOLT_ID = 268280089479872513L

fun main(args: Array<String>) {
    val client = createDiscordClient(args[0])!!
    println("Client Created")
}

fun createDiscordClient(token: String): IDiscordClient? {
    try {
        val builder = ClientBuilder().withToken(token)
                .registerListener(OnMessageListener())
        builder.login()
        return builder.build()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

class OnMessageListener : IListener<MessageReceivedEvent> {

    override fun handle(event: MessageReceivedEvent) {
        val message = event.message
        val client = event.client
        val channel = event.channel
        if ((message.author as User).longID == OWNER_ID && message.content.startsWith("\$\$")) {
            //Owner Commands
            //PreFix is "$$"
            val content = message.content.substring(2)
            when (content) {
                "stop" -> kotlin.run {
                    println("Stop Bot by Command")
                    channel.sendMessage("Stop Bot by Owner Command")
                    client.logout()
                }
            }
            if (content.startsWith("repeat")) {
                channel.sendMessage(content.substring(6))
            }
        }
        val content = message.content
        //Public Command
        if (content.startsWith("""$/""")) {
            val commands = content.substring(2).split(' ')
            when (commands[0]) {
                "rand" -> channel.sendMessage("名言(笑)その1: 二次元のロリ正義だ！ by ${getMention(client, COBOLT_ID)}")
            }
        }
        if (content.contains(".*(わかりゅ).*".toRegex())) {
            channel.sendMessage("わかりゅを検知")
        }
        if (content.contains(".*(NullPointerException|ぬるぽ).*".toRegex())){
            channel.sendMessage("${message.author.mention()} ■━⊂(　・∀・) 彡 ｶﾞｯ☆`Д´)ﾉ")
        }
        if (content.contains(".*(コボルト).*".toRegex())) {
            channel.sendMessage("名言集その1: 僕はコバルトだっ！コボルトじゃない！\n by ${getMention(client, COBOLT_ID)} ")
        }
    }
}

private fun getMention(client: IDiscordClient, id: Long): String {
    return client.getUserByID(id).mention()
}