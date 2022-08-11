package com.devsuperior.bds04.repository.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError() {
        super();
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(String fieldMessage, String message){
        errors.add(new FieldMessage(fieldMessage, message));

    }
}
