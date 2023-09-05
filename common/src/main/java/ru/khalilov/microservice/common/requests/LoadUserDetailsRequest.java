package ru.khalilov.microservice.common.requests;

import lombok.Builder;

@Builder
public record LoadUserDetailsRequest(String Username) {
}
