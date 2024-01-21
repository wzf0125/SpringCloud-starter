package org.quanta.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 认证数据
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordAuthDTO implements AuthDTO {
    @Builder.Default
    private String grant_type = "password";
    private String username;
    private String password;
}
