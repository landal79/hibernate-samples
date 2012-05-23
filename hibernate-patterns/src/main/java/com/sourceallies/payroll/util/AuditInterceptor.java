package com.sourceallies.payroll.util;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.sourceallies.payroll.domain.BaseEntity;
import com.sourceallies.payroll.domain.Auditable;

import java.io.Serializable;
import java.util.Date;

public class AuditInterceptor extends EmptyInterceptor {
    	
	private static final long serialVersionUID = -2257743977115894956L;

	@Override
    public boolean onSave(Object entity, Serializable id, Object[] currentState, String[] propertyNames, Type[] types) {
        boolean updated = false;

        if(entity instanceof Auditable) {
            Auditable a = (Auditable) entity;
            String username = getCurrentUser();
            Date stamp = new Date();
            for(int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];

                updated |= checkCreatedOn(currentState, a, stamp, i, propertyName);
                updated |= checkCreatedBy(currentState, a, username, i, propertyName);
                updated |= checkUpdatedOn(currentState, a, stamp, i, propertyName);
                updated |= checkUpdatedBy(currentState, a, username, i, propertyName);
            }
        }

        return updated;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        boolean updated = false;
        if(entity instanceof BaseEntity) {
            Auditable a = (Auditable) entity;
            String username = getCurrentUser();
            Date stamp = new Date();
            for(int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];
                updated |= checkUpdatedOn(currentState, a, stamp, i, propertyName);
                updated |= checkUpdatedBy(currentState, a, username, i, propertyName);
            }
        }

        return updated;
    }

    private boolean checkCreatedOn(Object[] currentState, Auditable auditable, Date stamp, int idx, String propertyName) {
        if(propertyName.equals("createdOn")) {
            auditable.setCreatedOn(stamp);
            currentState[idx] = stamp;
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkCreatedBy(Object[] currentState, Auditable auditable, String username, int idx, String propertyName) {
        if(propertyName.equals("createdBy")) {
            auditable.setCreatedBy(username);
            currentState[idx] = username;
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkUpdatedOn(Object[] currentState, Auditable auditable, Date stamp, int idx, String propertyName) {
        if(propertyName.equals("updatedOn")) {
            auditable.setUpdatedOn(stamp);
            currentState[idx] = stamp;
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkUpdatedBy(Object[] currentState, Auditable auditable, String username, int idx, String propertyName) {
        if(propertyName.equals("updatedBy")) {
            auditable.setUpdatedBy(username);
            currentState[idx] = username;
            return true;
        }
        else {
            return false;
        }
    }

    private String getCurrentUser() {
        return "Anonymous";
    }
}
