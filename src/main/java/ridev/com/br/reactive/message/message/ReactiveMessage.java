package ridev.com.br.reactive.message.message;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class ReactiveMessage {
    private final MessageBuilder message;
    private boolean removeReactionOnReact;
    private int reactionCooldown;
    private final HashMap<Emote, BiConsumer<Message, Member>> reacts;
    private boolean reportStack;

    private Message messageSended;
    private Emote emoji;
    private Consumer<Message> t;

    public ReactiveMessage(EmbedBuilder message) {
        this.removeReactionOnReact = false;
        this.message = new MessageBuilder(message);
        this.reactionCooldown = 2;
        this.reacts = new HashMap<>();
        this.reportStack = true;
    }
    public ReactiveMessage(String message) {
        this.removeReactionOnReact = false;
        this.message = new MessageBuilder(message);
        this.reactionCooldown = 2;
        this.reacts = new HashMap<>();
        this.reportStack = true;
    }

    public ReactiveMessage reportStack(boolean report) {
        this.reportStack = report;
        return this;
    }

    public ReactiveMessage reactionCooldown(int reactionCooldown) {
        this.reactionCooldown = reactionCooldown;
        return this;
    }
    public ReactiveMessage removeReactionOnReact(boolean removeReactionOnReact) {
        this.removeReactionOnReact = removeReactionOnReact;
        return this;
    }


    public Message toMessage() {
        return this.message.build();
    }

    public List<Emote> getEmotes() {
        List<Emote> emotes = new ArrayList<>();
        for(Map.Entry<Emote, BiConsumer<Message, Member>> mojis : this.reacts.entrySet()) {
            emotes.add(mojis.getKey());
        }
        return emotes;
    }


    public void addReact(Emote emoji, BiConsumer<Message, Member> t) {
        this.reacts.put(emoji, t);
    }

    public void sendReactiveMessage(TextChannel channel) {
        channel.sendMessage(this.toMessage()).queue(r -> {
            this.messageSended = r;
            ReactiveMessageLibrary.messages.put(r.getId(), this);
            for(Emote emojis : this.getEmotes()) {
                r.addReaction(emojis).queue();
//                this.reacts2.get(emojis).apply(r);
            }
        });
    }

    public Message getMessage() {
        return this.messageSended;
    }

}
