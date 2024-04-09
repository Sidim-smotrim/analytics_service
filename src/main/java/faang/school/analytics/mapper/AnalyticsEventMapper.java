package faang.school.analytics.mapper;

import faang.school.analytics.dto.analytics.AnalyticsEventDto;
import faang.school.analytics.event.UserPremiumBoughtEvent;
import faang.school.analytics.model.AnalyticsEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalyticsEventMapper {

    List<AnalyticsEventDto> toDto(List<AnalyticsEvent> analyticsEvents);

    AnalyticsEvent toEntity(AnalyticsEventDto analyticsEventDto);

    AnalyticsEventDto toDto(AnalyticsEvent analyticsEvent);

    @Mapping(target = "receiverId", source = "userId")
    @Mapping(target = "actorId", source = "userId")
    AnalyticsEventDto toDto(UserPremiumBoughtEvent userPremiumBoughtEvent);
}
