package com.buenSabor.BackEnd.models.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Long id;

}