package br.com.api.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/cadastrarUsuario", 
                    "/login",
                    "/listarAgendamentos",
                    "/alterarAgendamento",
                    "/cadastrarAgendamento",
                    "/deletarAgendamento/{id}",
                    "/listarMedicos",
                    "/alterarMedico",
                    "/cadastrarMedico",
                    "/deletarMedico/{id}",
                    "/listarusuarios",
                    "/listarPacientes",
                    "/alterarPaciente",
                    "/cadastrarPaciente",
                    "/deletarPaciente/{id}",
                    "/recuperarSenha",
                    "/deletarUsuario/{id}",
                    "/verificarCpf",
                    "/medico/{id}",
                    "/paciente/{id}",
                    "/agendamento/{id}",
                    "/verificarCodigo",
                    "/alterarSenha"
                ).permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

