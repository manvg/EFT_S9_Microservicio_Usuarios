package com.crud.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id){
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario createUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario){
        if (usuarioRepository.existsById(id)) {
            usuario.setIdUsuario(id);
            return usuarioRepository.save(usuario);
        }else{
            return null;
        }
    }

    @Override
    public void deleteUsuario(Integer id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public ResponseModel validarLogin(String email, String contrasena){
        boolean status = false;
        String message = "";

        Usuario usuario = usuarioRepository.findByemail(email);
        if (usuario != null && usuario.getcontrasena().equals(contrasena)) {
            status = true;
            message = "Login realizado con éxito.";
        }else{
            message = "Usuario y/o contraseña no válidos.";
        }

        return new ResponseModel(status, message);
    }
}
