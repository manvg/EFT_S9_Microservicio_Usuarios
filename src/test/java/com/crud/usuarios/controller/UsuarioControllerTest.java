package com.crud.usuarios.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.crud.usuarios.model.entities.Perfil;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.service.Usuario.UsuarioService;

@WebMvcTest(UsuarioController.class)//Referencia al controlador principal de usuarios
public class UsuarioControllerTest {
    //Configuraciones de los Mocks para simular las solicitudes HTTP al controlador
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioServiceMock;

    private Usuario usuario1;
    private Usuario usuario2;
    private Perfil perfil1;
    private Perfil perfil2;
    
    //Se ejecuta antes de cada metodo de pruebas
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
    @DisplayName("Obtener todos los usuarios => Respuesta correcta 200 OK")
    public void getAllUsuariosTest() throws Exception{

        List<Usuario> usuarios = List.of(usuario1, usuario2);
        when(usuarioServiceMock.getAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList.length()").value(2))
                .andExpect(jsonPath("$._embedded.usuarioList[0].idUsuario").value(1))
                .andExpect(jsonPath("$._embedded.usuarioList[0].nombre").value("Manuel"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].apellidoPaterno").value("Valdés"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].apellidoMaterno").value("Guerra"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].email").value("manuel@gmail.com"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].contrasena").value("manuel2024"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].telefono").value("98546854"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].direccion").value("Direccion de prueba"))
                .andExpect(jsonPath("$._embedded.usuarioList[0]._links.self.href").value("http://localhost/usuarios/1"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].idUsuario").value(2))
                .andExpect(jsonPath("$._embedded.usuarioList[1].nombre").value("Alfredo"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].apellidoPaterno").value("Guerra"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].apellidoMaterno").value("Valdés"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].email").value("alfredo@gmail.com"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].contrasena").value("alfredo2024"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].telefono").value("85468598"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].direccion").value("Otra Direccion de prueba"))
                .andExpect(jsonPath("$._embedded.usuarioList[1]._links.self.href").value("http://localhost/usuarios/2"))
                ;
    }

}
