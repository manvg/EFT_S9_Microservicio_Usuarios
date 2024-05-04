package com.crud.usuarios.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crud.usuarios.model.entities.Perfil;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.repository.Usuario.UsuarioRepository;

//Pruebas capa de acceso a datos
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//evita sustitución por una BD embebida. Asegura que la BD sea la misma que la configurada para el entorno de pruebas.
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario nuevoUsuario;
    private Perfil perfil1;
    
    //Se ejecuta antes de cada metodo de pruebas
    @BeforeEach
    public void InicializarPruebas(){
        //Inicializa perfiles de prueba
        perfil1 = new Perfil();
        perfil1.setIdPerfil(1);
        perfil1.setNombre("Administrador");

        //Inicializa usuarios de prueba
        nuevoUsuario = new Usuario();
        nuevoUsuario.setIdUsuario(1);
        nuevoUsuario.setNombre("Manuel");
        nuevoUsuario.setApellidoPaterno("Valdés");
        nuevoUsuario.setApellidoMaterno("Guerra");
        nuevoUsuario.setEmail("manuel@gmail.com");
        nuevoUsuario.setcontrasena("manuel2024");
        nuevoUsuario.setTelefono("998546854");
        nuevoUsuario.setDireccion("Direccion de prueba");
        nuevoUsuario.setPerfil(perfil1);
    }

    @Test
    @DisplayName("Guardar nuevo usuario => Respuesta correcta")
    public void guardarAtencionMedicaTest() {
        Usuario resultado = usuarioRepository.save(nuevoUsuario);

        assertNotNull(resultado.getIdUsuario());
        assertEquals("Valdés", resultado.getApellidoPaterno());
        assertEquals("Guerra", resultado.getApellidoMaterno());
    }

    @Test
    @DisplayName("Buscar usuario por correo electrónico => Respuesta correcta")
    public void buscarUsuarioPorEmailTest() {
        usuarioRepository.save(nuevoUsuario);

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByemail("manuel@gmail.com");

        assertNotNull(usuarioEncontrado);
        assertEquals("manuel@gmail.com", usuarioEncontrado.get().getEmail());
        assertEquals("Manuel", usuarioEncontrado.get().getNombre());
    }

}
