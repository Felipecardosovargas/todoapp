package com.teach.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criar uma nova tarefa")
public class TaskRequest {

    @NotBlank(message = "O título não pode estar em branco")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    @Schema(description = "Título da tarefa", example = "Comprar mantimentos", required = true)
    private String title;

    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Schema(description = "Descrição detalhada da tarefa", example = "Ir ao supermercado e comprar leite, pão e ovos.", required = true)
    private String description;
}