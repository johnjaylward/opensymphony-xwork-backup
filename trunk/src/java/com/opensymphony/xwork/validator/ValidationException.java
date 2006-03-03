/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.validator;


/**
 * ValidationException
 * <p/>
 * Created : Jan 20, 2003 11:45:07 PM
 *
 * @author Jason Carreira
 */
public class ValidationException extends Exception {

    /**
     * Constructs an <code>Exception</code> with no specified detail message.
     */
    public ValidationException() {
    }

    /**
     * Constructs an <code>Exception</code> with the specified detail message.
     *
     * @param s the detail message.
     */
    public ValidationException(String s) {
        super(s);
    }
}
