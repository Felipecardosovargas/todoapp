package com.teach.todoapp.dto;

import com.teach.todoapp.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualizar parcialmente uma tarefa (PATCH)")
public class TaskUpdateRequest {

    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    @Schema(description = "Novo título da tarefa (opcional)", example = "Comprar mantimentos atualizado")
    private String title;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Schema(description = "Nova descrição da tarefa (opcional)", example = "Comprar leite desnatado e ovos orgânicos.")
    private String description;

    @Schema(description = "Novo status da tarefa (opcional)", example = "CONCLUIDA")
    private TaskStatus status;
}