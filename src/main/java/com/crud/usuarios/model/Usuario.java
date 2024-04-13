package com.crud.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "nombre")
    @NotNull(message = "El campo nombre no puede ser nulo")
    @NotBlank
    private String nombre;

    @Column(name = "apellido_paterno")
    @NotNull(message = "El campo apellido paterno no puede ser nulo")
    @NotBlank
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    @NotNull(message = "El campo apellido materno no puede ser nulo")
    @NotBlank
    private String apellidoMaterno;

    @Column(name = "email")
    @NotNull(message = "El campo email no puede ser nulo")
    @NotBlank
    private String email;

    @Column(name = "contrasena")
    @NotNull(message = "El campo apellido paterno no puede ser nulo")
    @NotBlank
    private String contrasena;

    @Column(name = "telefono")
    private int telefono;
    
    @Column(name = "direccion")
    @NotNull(message = "El campo direccion no puede ser nulo")
    @NotBlank
    private String direccion;

    @ManyToOne(targetEntity = Perfil.class)
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public String getcontrasena() {
        return contrasena;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setcontrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
