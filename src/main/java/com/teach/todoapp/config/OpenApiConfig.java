package com.teach.todoapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TodoApp API - Projeto Bootcamp Full Stack",
                version = "1.0.0",
                description = "API RESTful desenvolvida como parte do Bootcamp Full Stack Java & React." +
                        " Este projeto foca no gerenciamento de tarefas (CRUD) com autenticação JWT," +
                        " seguindo as melhores práticas de desenvolvimento backend com Spring Boot." +
                        " O objetivo é demonstrar habilidades em: Gerenciamento de Tarefas, Autenticação e Autorização," +
                        " Tratamento Global de Exceções e Documentação de API." +
                        " Adicionei o site do Bootcamp na área de terms of service!",
                termsOfService = "https://main.d3uk3m3g9xv2ll.amplifyapp.com/",
                contact = @Contact(
                        name = "Felipe Cardoso Vargas",
                        url = "https://github.com/Felipecardosovargas"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor Local de Desenvolvimento"),
        }
)
public class OpenApiConfig {
}