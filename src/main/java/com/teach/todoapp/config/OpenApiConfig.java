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
                title = "TodoApp API",
                version = "1.0",
                description = "API para gerenciamento de tarefas (TodoApp).",
                termsOfService = "http://suaempresa.com/termos", // Opcional: URL dos termos de serviço
                contact = @Contact(
                        name = "Seu Nome/Empresa",
                        email = "contato@suaempresa.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor Local de Desenvolvimento"),
                // @Server(url = "https://suaapi.com.br", description = "Servidor de Produção") // Opcional: Adicionar mais servidores
        }
)
public class OpenApiConfig {
    // Esta classe não precisa de métodos, as anotações fazem o trabalho
}