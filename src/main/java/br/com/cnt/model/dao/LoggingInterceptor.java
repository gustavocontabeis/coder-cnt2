package br.com.cnt.model.dao;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class LoggingInterceptor extends EmptyInterceptor {
	
	Logger LOGGER = Logger.getLogger(LoggingInterceptor.class);
	
    @Override
    public boolean onFlushDirty(
        Object entity,
        Serializable id,
        Object[] currentState,
        Object[] previousState,
        String[] propertyNames,
        Type[] types) {
            System.out.printf("onFlushDirty: Entidade %s %s alterada de %s para %s\n",
                entity.getClass().getSimpleName(),
                id,
                Arrays.toString( previousState ),
                Arrays.toString( currentState )
            );
            return super.onFlushDirty( entity, id, currentState,
                previousState, propertyNames, types
        );
    }
    
    @Override
    public boolean onSave(
    		Object entity, 
    		Serializable id, 
    		Object[] state, 
    		String[] propertyNames, 
    		Type[] types) {
    	
    	System.out.printf("Entidade %s %s alterada de %s para %s\n", 
    			entity, 
        		id, 
        		state, 
        		Arrays.toString(propertyNames), 
        		Arrays.toString(types));

    	return super.onSave(entity, id, state, propertyNames, types);
    }
    
}