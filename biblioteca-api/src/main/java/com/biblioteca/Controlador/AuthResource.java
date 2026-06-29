package com.biblioteca.Controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @POST
    @Path("/login")
    public Response login(@QueryParam("usuario") String usuario, 
                          @QueryParam("password") String password) {
        
        Map<String, String> respuesta = new HashMap<>();
        
        // Simulación de autenticación
        if ("admin".equals(usuario) && "1234".equals(password)) {
            respuesta.put("mensaje", "Login exitoso");
            respuesta.put("token", "token-simulado-12345");
            respuesta.put("usuario", usuario);
            return Response.ok(respuesta).build();
        } else {
            respuesta.put("mensaje", "Credenciales incorrectas");
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(respuesta)
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Sesión cerrada exitosamente");
        return Response.ok(respuesta).build();
    }

    @GET
    @Path("/verificar")
    public Response verificarToken(@HeaderParam("Authorization") String token) {
        Map<String, String> respuesta = new HashMap<>();
        
        if (token != null && token.startsWith("Bearer ")) {
            respuesta.put("mensaje", "Token válido");
            respuesta.put("estado", "autenticado");
            return Response.ok(respuesta).build();
        } else {
            respuesta.put("mensaje", "Token no proporcionado o inválido");
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(respuesta)
                    .build();
        }
    }
}