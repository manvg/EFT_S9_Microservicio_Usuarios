package com.crud.usuarios.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.usuarios.model.dto.ResponseModel;
import com.crud.usuarios.model.dto.UsuarioDto;
import com.crud.usuarios.model.entities.Usuario;
import com.crud.usuarios.repository.UsuarioRepository;
import com.crud.usuarios.utilities.UsuarioMapper;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDto> getAllUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuarioMapper::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id){
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario createUsuario(UsuarioDto usuarioDto){
        Usuario usuario = usuarioMapper.convertirAEntity(usuarioDto);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, UsuarioDto usuarioDto){
        if (usuarioRepository.existsById(id)) {
            Usuario usuario = usuarioMapper.convertirAEntity(usuarioDto);
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
