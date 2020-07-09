package com.feed.news.entity;

import com.feed.news.validation.PasswordsEqualConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@PasswordsEqualConstraint(first = "password", second = "confirmPassword", message = "The password fields must match")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDto {

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    private String token;

}
