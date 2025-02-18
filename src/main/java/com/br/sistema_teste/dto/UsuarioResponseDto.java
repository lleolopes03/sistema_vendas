package com.br.sistema_teste.dto;

public class UsuarioResponseDto {
    private Long id;
    private String username;
    private String email;
    private int age;
    private String role;

    public UsuarioResponseDto() {
    }

    public UsuarioResponseDto(Long id, String username, String email, int age, String role) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
