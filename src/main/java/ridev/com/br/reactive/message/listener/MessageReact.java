package ridev.com.br.reactive.message.listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.reactive.message.cooldown.RiCooldown;
import ridev.com.br.reactive.message.message.ReactiveMessage;
import ridev.com.br.reactive.message.message.ReactiveMessageLibrary;

import java.util.Objects;


public class MessageReact extends ListenerAdapter {

    private final JDA bot;

    public MessageReact(JDA bot) {
        bot.addEventListener(this);
        this.bot = bot;
    }


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent e) {
        Member user = e.getMember();
        if (user == null) return;
        if (Objects.requireNonNull(e.getMember()).getId().equalsIgnoreCase(bot.getSelfUser().getId())) return;
        if (!ReactiveMessageLibrary.messages.containsKey(e.getMessageId())) return;
        ReactiveMessage message = ReactiveMessageLibrary.messages.get(e.getMessageId());
        boolean hasPerm = false;
        for (Role cargos : e.getJDA().getRoles()) {
            if (cargos.getPermissions().contains(Permission.MESSAGE_MANAGE)) {
                hasPerm = true;
                break;
            }
        }
            if (!hasPerm && message.isReportStack()) {
                System.out.println("The bot not have the permission MESSAGE_MANAGE. Please active it. \nGuild: " + e.getGuild().getName() + "\nMessage ID: " + e.getMessageId());
            } else {
                if (RiCooldown.isInCooldown(e.getMessageId(), "react")) {
                    e.getReaction().removeReaction(user.getUser()).queue();
                } else {
                Emote reacted = e.getReaction().getReactionEmote().getEmote();
                if (message != null) {
                    RiCooldown cd = new RiCooldown(e.getMessageId(), "react", message.getReactionCooldown());
                    message.getReacts().get(reacted).accept(message.getMessage(), user);
                    if (message.isRemoveReactionOnReact()) e.getReaction().removeReaction(user.getUser()).queue();
                    cd.start();
                }
            }
        }
    }
}

