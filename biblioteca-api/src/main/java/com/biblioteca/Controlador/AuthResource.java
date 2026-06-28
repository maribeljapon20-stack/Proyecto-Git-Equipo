package com.biblioteca.Controlador;

import com.biblioteca.Entidad.Usuario;
import com.biblioteca.Seguridad.LoginRequest;
import com.biblioteca.Seguridad.TokenResponse;
import com.biblioteca.Servicio.UsuarioServicio;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Set;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    
    @Inject
    UsuarioServicio usuarioServicio;
    
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        Usuario usuario = usuarioServicio.autenticar(request.cedula, request.password);
        
        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciales inválidas")
                    .build();
        }
        
        // ✅ CORRECTO: Sin punto y coma hasta el final
        String token = Jwt.issuer("biblioteca-api")
                .upn(usuario.cedula)
                .groups(Set.of("USER"))
                .expiresIn(3600)
                .sign();
        
        return Response.ok(new TokenResponse(token)).build();
    }
}