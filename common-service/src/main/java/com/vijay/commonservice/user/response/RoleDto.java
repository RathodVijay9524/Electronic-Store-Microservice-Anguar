package com.vijay.commonservice.user.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private String roleId;
    private String roleName;
}
