import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import ridev.com.br.reactive.message.RiReactiveMessage;
import ridev.com.br.reactive.message.message.ReactiveMessage;

import javax.security.auth.login.LoginException;

public class TestMain {

    static JDA bot;
    static RiReactiveMessage reactive;

    public static void main(String[] args) throws LoginException, InterruptedException {

        // BOT MAKING
        JDABuilder builder = JDABuilder.createDefault("TOKEN");
        bot = builder.build();
        bot.awaitReady();



        // CONFIGURATION
        RiReactiveMessage message = new RiReactiveMessage(bot);
        message.initialize();
        reactive = message;


        // CHANNEL INFO
        Guild guilda = bot.getGuildById("GUILDID");
        assert guilda != null;
        Emote emoji = guilda.getEmotes().get(0);
        TextChannel canal = guilda.getTextChannelById("CHANNELID");


        // MESSAGE INFO
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("teste");
        ReactiveMessage embedToReact = new ReactiveMessage(embed)
                .removeReactionOnReact(true)
                .reactionCooldown(1);

        // REACTING
        assert canal != null;
        embedToReact.addReact(emoji, (msg, user) -> {
            EmbedBuilder builder3 = new EmbedBuilder().setDescription("eae");
            MessageBuilder embedsla = new MessageBuilder(builder3.build());
            msg.editMessage(embedsla.build()).queue();
            System.out.println(user.getEffectiveName());
        });

        // SEND REACTIVE MESSAGE
        embedToReact.sendReactiveMessage(canal);
    }
}
