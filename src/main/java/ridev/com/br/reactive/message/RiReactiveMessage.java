package ridev.com.br.reactive.message;

import net.dv8tion.jda.api.JDA;
import ridev.com.br.reactive.message.listener.MessageReact;

public class RiReactiveMessage {
    private final JDA bot;

    public RiReactiveMessage(JDA bot) {
        this.bot = bot;
    }


    public void initialize(){
        new MessageReact(this.bot);
    }

}
