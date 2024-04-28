package faang.school.analytics.config.redis;

import faang.school.analytics.listener.postview.PostViewEventListener;
import faang.school.analytics.listener.projectview.ProjectViewEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisListenerConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channel.post-view}")
    private String postViewTopic;
    @Value("${spring.data.redis.channel.project-view}")
    private String projectViewChannel;
    private final PostViewEventListener postViewListener;
    private final ProjectViewEventListener projectViewEventListener;

    @Bean
    ChannelTopic postViewTopic() {
        return new ChannelTopic(postViewTopic);
    }

    @Bean
    public ChannelTopic projectViewTopic() {
        return new ChannelTopic(projectViewChannel);
    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public MessageListenerAdapter postViewListenerAdapter() {
        return new MessageListenerAdapter(postViewListener);
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(projectViewEventListener);
    }

    @Bean
    public RedisMessageListenerContainer postViewMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(postViewListenerAdapter(), postViewTopic());
        container.addMessageListener(messageListener(), projectViewTopic());
        return container;
    }
}