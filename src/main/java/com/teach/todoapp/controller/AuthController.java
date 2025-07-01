package com.teach.todoapp.controller;

import com.teach.todoapp.dto.AuthRequest;
import com.teach.todoapp.dto.AuthResponse;
import com.teach.todoapp.dto.ErrorResponse;
import com.teach.todoapp.dto.UserRequest;
import com.teach.todoapp.security.JwtTokenProvider;
import com.teach.todoapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "APIs para registro e login de usuários")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário e gerar token JWT",
            description = "Realiza o login de um usuário e retorna um token de acesso JWT válido.")
    @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida, token JWT retornado.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida (e.g., campos faltando/inválidos na validação do DTO).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas (usuário/senha incorretos).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário",
            description = "Cria um novo usuário no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso.",
            content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Usuário registrado com sucesso!")))
    @ApiResponse(responseCode = "400", description = "Requisição inválida (e.g., campos faltando/inválidos na validação do DTO).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Conflito de dados (e.g., nome de usuário já existe).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
        authService.registerUser(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}