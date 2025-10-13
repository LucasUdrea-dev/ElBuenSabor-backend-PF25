package com.buenSabor.BackEnd.models.audit;

import com.buenSabor.BackEnd.config.CustomRevisionListener;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REVISION_INFO")
@RevisionEntity(CustomRevisionListener.class)
@Data
public class Revision implements Serializable {

    //Genera Tabla RevisionInfo
    //   id / revision_date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private long id;

    @Column(name = "REVISION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @RevisionTimestamp
    private Date date;

    /*
    Para generar tablas con audit se debe crear las anotaciones de las entidades que queremos auditar
    , en cada entidad podemos guardar todos los atributos o seleccionar los que no queramos

    @Entity
    @Audited // Para auditoria
    public class Persona extends Bean {

        private String nombre;
        private String apellido;

        @NotAudited // Indica que este atributo no va a ser auditado
        private int dni;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "fk_domicilio")
        @NotAudited // Indica que este atributo no va a ser auditado
        private Domicilio domicilio;

    }

    Genera tabla con entidad Persona y tabla Persona_aud
    Persona_aud
    id / rev / revtype / apellido / nombre

    revtype: 0---> Creado
    revtype: 1---> Modificado
    revtype: 2---> Eliminado

    */

}
