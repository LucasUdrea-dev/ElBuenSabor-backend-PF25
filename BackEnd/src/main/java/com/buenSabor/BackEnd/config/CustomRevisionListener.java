package com.buenSabor.BackEnd.config;

import com.buenSabor.BackEnd.models.audit.Revision;
import org.hibernate.envers.RevisionListener;

//Config Auditoria
public class CustomRevisionListener implements RevisionListener {

    public  void newRevision(Object revisionEntity){
        final Revision revision = (Revision) revisionEntity;
    }
}
