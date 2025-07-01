package com.teach.todoapp.controller;

import com.teach.todoapp.dto.ErrorResponse;
import com.teach.todoapp.dto.TaskRequest;
import com.teach.todoapp.dto.TaskUpdateRequest;
import com.teach.todoapp.model.Task;
import com.teach.todoapp.model.TaskStatus;
import com.teach.todoapp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "APIs para gerenciamento de tarefas do usuário")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(summary = "Listar tarefas do usuário",
            description = "Retorna uma lista de tarefas do usuário autenticado, opcionalmente filtradas por status.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado (token JWT ausente ou inválido).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<Task> getTasks(
            Authentication authentication,
            @Parameter(description = "Status da tarefa para filtrar (PENDENTE, EM_ANDAMENTO, CONCLUIDA)", example = "PENDENTE")
            @RequestParam(required = false) TaskStatus status) {
        return taskService.getTasksByUser(authentication.getName(), status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter tarefa por ID",
            description = "Retorna uma tarefa específica do usuário autenticado pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Tarefa encontrada e retornada.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado (token JWT ausente ou inválido).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado (a tarefa não pertence ao usuário autenticado).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada com o ID fornecido.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "ID da tarefa a ser buscada", example = "1")
            @PathVariable Long id,
            Authentication authentication) {
        Task task = taskService.getTaskById(id, authentication.getName());
        return ResponseEntity.ok(task);
    }

    @PostMapping
    @Operation(summary = "Criar nova tarefa",
            description = "Cria uma nova tarefa para o usuário autenticado. O status inicial é 'PENDENTE'.")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida (e.g., campos faltando/inválidos na validação do DTO).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado (token JWT ausente ou inválido).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody TaskRequest taskRequest,
            Authentication authentication) {
        Task task = new Task();
        task.setTitulo(taskRequest.getTitle());
        task.setDescricao(taskRequest.getDescription());

        Task createdTask = taskService.createTask(task, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar tarefa parcialmente",
            description = "Atualiza campos específicos de uma tarefa existente pelo seu ID. Permite atualização parcial (PATCH).")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida (e.g., campos inválidos na validação do DTO).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado (token JWT ausente ou inválido).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado (a tarefa não pertence ao usuário autenticado).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada com o ID fornecido.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Task> updateTask(
            @Parameter(description = "ID da tarefa a ser atualizada", example = "1")
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest taskDetails,
            Authentication authentication) {

        Task taskToUpdate = new Task();
        taskToUpdate.setTitulo(taskDetails.getTitle());
        taskToUpdate.setDescricao(taskDetails.getDescription());
        taskToUpdate.setStatus(taskDetails.getStatus());

        Task updatedTask = taskService.updateTask(id, taskToUpdate, authentication.getName());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tarefa por ID",
            description = "Exclui uma tarefa específica do usuário autenticado pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Tarefa excluída com sucesso.",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "401", description = "Não autorizado (token JWT ausente ou inválido).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado (a tarefa não pertence ao usuário autenticado).",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada com o ID fornecido.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<?> deleteTask(
            @Parameter(description = "ID da tarefa a ser excluída", example = "1")
            @PathVariable Long id,
            Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}