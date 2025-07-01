package com.teach.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requisição para registro de novo usuário")
public class UserRequest {
    @NotBlank(message = "O nome de usuário não pode estar em branco")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres")
    @Schema(description = "Nome de usuário para registro", example = "novo.usuario")
    private String username;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Schema(description = "Senha do usuário para registro", example = "senhaSegura456")
    private String password;
}