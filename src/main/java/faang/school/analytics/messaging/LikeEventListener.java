package faang.school.analytics.messaging;

import faang.school.analytics.dto.LikeEvent;
import faang.school.analytics.service.like.LikeEventWorker;
import faang.school.analytics.util.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeEventListener implements MessageListener {

    private final JsonMapper jsonMapper;
    private final LikeEventWorker likeEventWorker;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        jsonMapper.toObject(message.toString(), LikeEvent.class)
                .ifPresent(s -> likeEventWorker.saveLikeEvent(s));
        log.info(message+ " " + "send");
    }
}
