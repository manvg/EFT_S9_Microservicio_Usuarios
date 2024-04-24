package com.crud.usuarios.service.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.repository.Usuario.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    //---------GET---------//
    @Override
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id){
        return usuarioRepository.findById(id);
    }

    //---------POST---------//
    @Override
    public Usuario createUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public ResponseModel validarUsuarioPorEmail(String email){
        String message = "";
        boolean status = false;

        var existeEmail = usuarioRepository.findByemail(email);
        if (!existeEmail.isEmpty()) {
            message = "Ya existe un usuario con el email '" + email+ "'";
        }else{
            message = "Puede continuar con la creación del usuario.";
            status = true;
        }
        return new ResponseModel(status, message);
    }

    @Override
    public ResponseModel validarLogin(String email, String contrasena){
        boolean status = false;
        String message = "";

        var usuario = usuarioRepository.findByemail(email);
        if (!usuario.isEmpty()) {
            if (usuario.get().getcontrasena().equals(contrasena)) {
                status = true;
                message = "Login realizado con éxito.";
            }else{
                message = "Usuario y/o contraseña no válidos.";
            }
        }else{
            message = "No existe un usuario asociado al email " + email;
        }
       

        return new ResponseModel(status, message);
    }

    //---------PUT---------//
    @Override
    public Usuario updateUsuario(Integer id, Usuario objUsuario){
        var usuarioExiste = usuarioRepository.findById(id);
        if (!usuarioExiste.isEmpty()) {
            Usuario usuario = usuarioExiste.get();
            usuario.setApellidoMaterno(objUsuario.getApellidoMaterno());
            usuario.setApellidoPaterno(objUsuario.getApellidoPaterno());
            usuario.setDireccion(objUsuario.getDireccion());
            usuario.setEmail(objUsuario.getEmail());
            usuario.setNombre(objUsuario.getNombre());
            usuario.setPerfil(objUsuario.getPerfil());
            usuario.setTelefono(objUsuario.getTelefono());
            usuario.setcontrasena(objUsuario.getcontrasena());
            usuario.setIdUsuario(id);
            //Actualizar usuario
            return usuarioRepository.save(usuario);
        }else{
            return null;
        }
    }

    //---------DELETE---------//
    @Override
    public ResponseModel deleteUsuario(Integer id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return new ResponseModel(true, "Usuario eliminado con éxito");
        }else{
            return new ResponseModel(false, "El usuario ingresado no existe");
        }
    }
}
