import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Bot {
    public static void main(final String[] args) {
        final String token;
        try {
            token = getToken("galaxia");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            return;
        }
        System.out.println();
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("Pong!").block();
            }
        });

        gateway.onDisconnect().block();
    }

    private static String getToken(String fileName) throws FileNotFoundException {
        File file = new File("" + fileName + ".token");
        System.out.println(file.getAbsolutePath());

        Scanner input = new Scanner(file);

        return input.next();
    }
}
