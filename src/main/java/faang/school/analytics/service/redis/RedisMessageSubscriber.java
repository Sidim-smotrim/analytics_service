package faang.school.analytics.service.redis;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RedisMessageSubscriber implements MessageListener {
  @Value("${spring.data.redis.channels.analytic_channel.name}")
  private String defaultChannelName;
  private List<String> subscribedChannels = new ArrayList<>();

  @PostConstruct
  private void postConstruct() {
    subscribedChannels.add(defaultChannelName);
  }

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channel = new String(message.getChannel());
    String body = new String(message.getBody());

    // Depends on channel name we can write here different logic
    log.info("Received message: " + body + " from channel: " + channel);
    System.out.println("Received message: " + body + " from channel: " + channel);
  }

  public void subscribe(String channel) {
    if (!subscribedChannels.contains(channel)) {
      subscribedChannels.add(channel);
    }
  }

  public void unsubscribe(String channel) {
    subscribedChannels.remove(channel);
  }

  public List<String> getSubscribedChannels() {
    return subscribedChannels;
  }
}