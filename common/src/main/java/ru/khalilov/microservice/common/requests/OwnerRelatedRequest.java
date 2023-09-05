package ru.khalilov.microservice.common.requests;

import lombok.Builder;
import ru.khalilov.microservice.common.models.UserRoles;

@Builder
public record OwnerRelatedRequest(Long OwnerId, String Name, String Surname, String Username, String Password, UserRoles Role, Long CatId) {
}
