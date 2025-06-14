/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.seguridad;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User_Authentication")
public class UserAuthentication extends Bean {

  

    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @OneToOne(mappedBy = "userAuthentication", fetch = FetchType.EAGER)
    @JsonIgnore
    private Usuario usuario;

    
}
