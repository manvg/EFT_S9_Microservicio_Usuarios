package com.crud.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.usuarios.model.entities.Perfil;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.repository.Usuario.UsuarioRepository;
import com.crud.usuarios.service.Usuario.UsuarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    //Configuraciones de los Mocks para simular las solicitudes
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepositoryMock;

    private Usuario usuario1;
    private Usuario usuario2;
    private Perfil perfil1;
    private Perfil perfil2;
    
    @BeforeEach
    public void InicializarPruebas(){
        //Inicializa perfiles de prueba
        perfil1 = new Perfil();
        perfil1.setIdPerfil(1);
        perfil1.setNombre("Administrador");

        perfil2 = new Perfil();
        perfil2.setIdPerfil(2);
        perfil2.setNombre("Ventas");

        //Inicializa usuarios de prueba
        usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        usuario1.setNombre("Manuel");
        usuario1.setApellidoPaterno("Valdés");
        usuario1.setApellidoMaterno("Guerra");
        usuario1.setEmail("manuel@gmail.com");
        usuario1.setcontrasena("manuel2024");
        usuario1.setTelefono("98546854");
        usuario1.setDireccion("Direccion de prueba");
        usuario1.setPerfil(perfil1);

        usuario2 = new Usuario();
        usuario2.setIdUsuario(2);
        usuario2.setNombre("Alfredo");
        usuario2.setApellidoPaterno("Guerra");
        usuario2.setApellidoMaterno("Valdés");
        usuario2.setEmail("alfredo@gmail.com");
        usuario2.setcontrasena("alfredo2024");
        usuario2.setTelefono("85468598");
        usuario2.setDireccion("Otra Direccion de prueba");
        usuario2.setPerfil(perfil2);

    }

    @Test
    @DisplayName("Guardar un usuario => Respuesta correcta")
    public void guardarUsuarioTest(){
        when(usuarioRepositoryMock.save(any())).thenReturn(usuario1);

        Usuario usuario = usuarioService.createUsuario(usuario1);
        assertEquals("manuel@gmail.com", usuario.getEmail());
        assertEquals("Manuel", usuario.getNombre());
        assertEquals("Valdés", usuario.getApellidoPaterno());
        assertEquals("Guerra", usuario.getApellidoMaterno());
    }
}
