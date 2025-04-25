package com.br.sistema_teste.dto;

import com.br.sistema_teste.domain.Usuario;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class UsuarioCreateDto {
    @NotBlank
    private String username;
    @NotBlank
    @Email(message = "formato do e-mail está inválido",regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    private int age;
    @NotBlank
    private String password;
    @NotNull(message = "O campo role não pode ser nulo")
    private Usuario.Role role;

    public UsuarioCreateDto() {
    }

    public UsuarioCreateDto(String username, String email, int age, String password, Usuario.Role role) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario.Role getRole() {
        return role;
    }

    public void setRole(Usuario.Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UsuarioCreateDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
