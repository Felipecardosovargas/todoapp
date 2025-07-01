package com.teach.todoapp.service;

import com.teach.todoapp.exception.ResourceNotFoundException;
import com.teach.todoapp.model.Task;
import com.teach.todoapp.model.TaskStatus;
import com.teach.todoapp.model.User;
import com.teach.todoapp.repository.TaskRepository;
import com.teach.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasksByUser(String username, TaskStatus status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        if (status != null) {
            return taskRepository.findByUserIdAndStatus(user.getId(), status);
        }
        return taskRepository.findByUserId(user.getId());
    }

    public Task getTaskById(Long id, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o ID: " + id));
        if (!task.getUser().getUsername().equals(username)) {
            throw new SecurityException("Acesso negado. Esta tarefa não pertence ao usuário: " + username);
        }
        return task;
    }

    @Transactional
    public Task createTask(Task task, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        task.setUser(user);
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDENTE);
        }
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails, String username) {
        Task task = getTaskById(id, username);

        if (taskDetails.getTitulo() != null) {
            task.setTitulo(taskDetails.getTitulo());
        }
        if (taskDetails.getDescricao() != null) {
            task.setDescricao(taskDetails.getDescricao());
        }
        if (taskDetails.getStatus() != null) {
            task.setStatus(taskDetails.getStatus());
        }
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id, String username) {
        Task task = getTaskById(id, username);
        taskRepository.delete(task);
    }
}