package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.dto.FollowerEventDto;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.model.EventType;
import faang.school.analytics.service.AnalyticsService;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
public class FollowerEventListener extends AbstractEventListener<FollowerEventDto> {

    private final AnalyticsService analyticsService;
    private final AnalyticsEventMapper analyticsEventMapper;

    public FollowerEventListener(ObjectMapper objectMapper, AnalyticsService analyticsService, AnalyticsEventMapper analyticsEventMapper) {
        super(objectMapper);
        this.analyticsService = analyticsService;
        this.analyticsEventMapper = analyticsEventMapper;
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        FollowerEventDto event = getEvent(message, FollowerEventDto.class);
        AnalyticsEvent analyticsEvent = analyticsEventMapper.toEntity(event);
        analyticsEvent.setEventType(EventType.FOLLOWER);
        analyticsService.saveEvent(analyticsEvent);
    }
}
