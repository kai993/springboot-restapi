package com.example.sample.application.controller.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Size(min = 5, max = 100, message = "{0は5文字以上100文字以下で指定してください。}")
    private String username;

    @Size(min = 5, max = 100, message = "{0は5文字以上100文字以下で指定してください。}")
    private String password;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "{0はyyyy-mm-dd形式で指定してください。}")
    private String birthdate;

    @Size(min = 1, max = 100, message = "{0は1文字以上100文字以下で指定してください。}")
    private String firstName;

    @Size(min = 1, max = 100, message = "{0は1文字以上100文字以下で指定してください。}")
    private String lastName;

    public LocalDate getBirthdate() {
        if (birthdate == null || birthdate.isEmpty()) {
            return null;
        }
        return LocalDate.parse(birthdate);
    }
}
