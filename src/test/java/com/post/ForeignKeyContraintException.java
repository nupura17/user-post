package com.post;

import org.springframework.dao.DataAccessException;

public class ForeignKeyContraintException extends DataAccessException {

    public ForeignKeyContraintException(){
        super("Foreign Key Contraint Error");
    }

}
