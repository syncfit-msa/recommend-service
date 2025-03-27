package com.amcamp.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ;

    private final String role;
}
