package com.crud.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.usuarios.model.Usuario;
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

}
