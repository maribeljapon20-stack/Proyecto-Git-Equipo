package com.biblioteca.Excepcion;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ManejadorGlobalExcepciones implements ExceptionMapper<Exception> {
    
    @Override
    public Response toResponse(Exception exception) {
        Map<String, Object> respuesta = new HashMap<>();
        
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException cvEx = (ConstraintViolationException) exception;
            String mensajes = cvEx.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            
            respuesta.put("error", "Error de validación");
            respuesta.put("mensaje", mensajes);
            return Response.status(Response.Status.BAD_REQUEST)
                          .entity(respuesta)
                          .build();
        }
        
        if (exception instanceof RecursoDuplicadoException) {
            respuesta.put("error", "Recurso duplicado");
            respuesta.put("mensaje", exception.getMessage());
            return Response.status(Response.Status.CONFLICT)
                          .entity(respuesta)
                          .build();
        }
        
        if (exception instanceof RuntimeException) {
            respuesta.put("error", "Error interno del servidor");
            respuesta.put("mensaje", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity(respuesta)
                          .build();
        }
        
        respuesta.put("error", "Error no manejado");
        respuesta.put("mensaje", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                      .entity(respuesta)
                      .build();
    }
}
