# RiReaction
Este é mais uma API de fácil compreensão! Nesta API adicionamos reações por emojis de uma maneira prática e rapida!


# Starting
Para iniciar, é necessário possuir a API em seu projeto, sendo ele maven ou gradle

Para este projeto eu utilizei 2 APIs cruciais para a criação da mesma:
   - [JDA (5.0.0)](https://github.com/DV8FromTheWorld/JDA)
   - [Lombok (1.18.22)](https://github.com/projectlombok/lombok)

Siga as instruções abaixo:

## Maven

```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
             <dependency>
            <groupId>com.github.yRicardinBaum</groupId>
            <artifactId>JDA-Reaction</artifactId>
            <version>v2.0</version>
        </dependency>
     </dependencies>
```

## Gradle
```groovy
repositories {
        mavenCentral()
        maven { url = 'https://jitpack.io/' }
}
dependencies {
    implementation 'com.github.yRicardinBaum:JDA-Reaction:v2.0'
}
```


# Utilização

Para começarmos, iremos criar a instancia na Main.
```java
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
    }
}
```

Prosseguindo, vamos enviar uma mensagem para algum servidor do bot, para isso vamos pegar as informações do canal a ser enviado:

```java
// CHANNEL INFO
        // PEGANDO ID DA GUILDA
        Guild guilda = bot.getGuildById("GUILDID");
        // ASSERT SE A GUILDA EXISTE
        assert guilda != null;
        // PEGANDO O PRIMEIRO EMOJI DO SERVIDOR
        Emote emoji = guilda.getEmotes().get(0);
        // PEGANDO UM CANAL QUALQUER COM O ID PELA GUILDA
        TextChannel canal = guilda.getTextChannelById("CHANNELID");
```

Agora iremos preparar a mensagem:

```java
        // MESSAGE INFO
        // CRIAÇÃO DA EMBED
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("teste");
        // INJETANDO A REAÇÃO NA EMBED
        ReactiveMessage embedToReact = new ReactiveMessage(embed)
                 // QUANDO O PLAYER REAGIR, O BOT RETIRA A REAÇÃO? 
                 //DEFAULT: FALSE
                .removeReactionOnReact(false)
                // TEMPO DELIMITADO PARA CADA REAÇÃO? 
                //DEFAULT: 2
                .reactionCooldown(2);
```

Agora iremos configurar os emojis a serem adicionados na mensagem:
```java
        // ADICIONANDO REAÇÃO
        embedToReact.addReact(/*EMOTE*/emoji, (/*MENSAGEM REAGIDA*/ msg, /*AUTOR DA REAÇÃO*/user) -> {
            // CONSTRUÇÃO DE UMA EMBED
            EmbedBuilder builder3 = new EmbedBuilder().setDescription("eae");
            MessageBuilder embedsla = new MessageBuilder(builder3.build());
            // EDITANDO A MENSAGEM AO PLAYER REAGIR
            msg.editMessage(embedsla.build()).queue();
            // ENVIARÁ NO CONSOLE O NOME DO PLAYER QUE REAGIU 
            System.out.println(user.getEffectiveName());
        });
```

# Finalização
E foi isso, está tudo funcionando. Caso tenha alguma duvida ou problema podem entrar no meu discord: [yRicardinBaum](https://discordapp.com/users/409801761470152704).

Discord: [Servidor do Discord](https://discord.gg/QG9WZVcD3J)
