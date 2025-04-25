package com.br.sistema_teste.dto;

import com.br.sistema_teste.domain.Usuario;



public class UsuarioResponseDto {
    private Long id;
    private String username;
    private String email;
    private int age;
    private Usuario.Role role = Usuario.Role.ROLE_CAIXA;

    public UsuarioResponseDto() {
    }

    public UsuarioResponseDto(Long id, String username, String email, int age, Usuario.Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Usuario.Role getRole() {
        return role;
    }

    public void setRole(Usuario.Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UsuarioResponseDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", role=" + role +
                '}';
    }
}
