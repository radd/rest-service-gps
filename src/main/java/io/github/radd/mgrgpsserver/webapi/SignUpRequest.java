package io.github.radd.mgrgpsserver.webapi;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 20)
    private String password;

    @NotBlank
    @Size(min = 4, max = 20)
    private String password2;

    @NotBlank
    @Size(min = 4, max = 40)
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
