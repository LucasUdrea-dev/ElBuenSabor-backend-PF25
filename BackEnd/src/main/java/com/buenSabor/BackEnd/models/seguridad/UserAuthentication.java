/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.seguridad;

import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.user.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User_Authentication")
public class UserAuthentication extends Bean implements UserDetails {

    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @Column(name = "firebase_uid", unique = true)
    private String firebaseUid;
    @OneToOne(mappedBy = "userAuthentication", fetch = FetchType.EAGER)
    private Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        Rol rol = usuario.getRol();

        if (rol != null && rol.getTipoRol() != null) {
            TypeRol tipo = rol.getTipoRol().getRol();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + tipo.name()));
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
