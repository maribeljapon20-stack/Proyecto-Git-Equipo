package com.biblioteca;

import com.biblioteca.Entidad.Usuario;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioResourceTest {

    @Test
    public void testUsuarioNombreCorto() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Li");
        usuario.setEmail("lennin@email.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        var violaciones = validator.validate(usuario);
        assertFalse(violaciones.isEmpty(), "Debería haber violaciones por nombre muy corto");
    }

    @Test
    public void testUsuarioEmailInvalido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Lennin");
        usuario.setEmail("email-invalido");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        var violaciones = validator.validate(usuario);
        assertFalse(violaciones.isEmpty(), "Debería haber violaciones por email inválido");
        assertEquals(1, violaciones.size(), "Debería haber exactamente 1 violación");
    }

    @Test
    public void testUsuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Lennin Japón");
        usuario.setEmail("lennin@email.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        var violaciones = validator.validate(usuario);
        assertTrue(violaciones.isEmpty(), "El usuario debería ser válido");
    }
}