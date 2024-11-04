package com.finance.backend.domains.user;

public record RegisterDTO(String login, String password, String email, UserRole role) {}
