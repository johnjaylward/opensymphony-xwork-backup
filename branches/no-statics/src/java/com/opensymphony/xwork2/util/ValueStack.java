/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork2.util;

import java.util.Map;

/**
 * ValueStack allows multiple beans to be pushed in and dynamic EL expressions to be evaluated against it. When
 * evaluating an expression, the stack will be searched down the stack, from the latest objects pushed in to the
 * earliest, looking for a bean with a getter or setter for the given property or a method of the given name (depending
 * on the expression being evaluated).
 */
public interface ValueStack {

    public static final String VALUE_STACK = "com.opensymphony.xwork2.util.ValueStack.ValueStack";

    public static final String REPORT_ERRORS_ON_NO_PROP = "com.opensymphony.xwork2.util.ValueStack.ReportErrorsOnNoProp";

    /**
     * Gets the context for this value stack. The context holds all the information in the value stack and it's surrondings.
     *
     * @return  the context.
     */
    public abstract Map getContext();

    /**
     * Sets the default type to convert to if no type is provided when getting a value.
     *
     * @param defaultType the new default type
     */
    public abstract void setDefaultType(Class defaultType);

    /**
     * Set a override map containing <code>key -> values</code> that takes precedens when doing find operations on the ValueStack.
     * <p/>
     * See the unit test for ValueStackTest for examples.
     *
     * @param overrides  overrides map.
     */
    public abstract void setExprOverrides(Map overrides);

    /**
     * Gets the override map if anyone exists.
     *
     * @return the override map, <tt>null</tt> if not set.
     */
    public abstract Map getExprOverrides();

    /**
     * Get the CompoundRoot which holds the objects pushed onto the stack
     *
     * @return the root
     */
    public abstract CompoundRoot getRoot();

    /**
     * Attempts to set a property on a bean in the stack with the given expression using the default search order.
     *
     * @param expr  the expression defining the path to the property to be set.
     * @param value the value to be set into the neamed property
     */
    public abstract void setValue(String expr, Object value);

    /**
     * Attempts to set a property on a bean in the stack with the given expression using the default search order.
     *
     * @param expr                    the expression defining the path to the property to be set.
     * @param value                   the value to be set into the neamed property
     * @param throwExceptionOnFailure a flag to tell whether an exception should be thrown if there is no property with
     *                                the given name.
     */
    public abstract void setValue(String expr, Object value, boolean throwExceptionOnFailure);

    public abstract String findString(String expr);

    /**
     * Find a value by evaluating the given expression against the stack in the default search order.
     *
     * @param expr the expression giving the path of properties to navigate to find the property value to return
     * @return the result of evaluating the expression
     */
    public abstract Object findValue(String expr);

    /**
     * Find a value by evaluating the given expression against the stack in the default search order.
     *
     * @param expr   the expression giving the path of properties to navigate to find the property value to return
     * @param asType the type to convert the return value to
     * @return the result of evaluating the expression
     */
    public abstract Object findValue(String expr, Class asType);

    /**
     * Get the object on the top of the stack <b>without</b> changing the stack.
     *
     * @return the object on the top.
     * @see CompoundRoot#peek()
     */
    public abstract Object peek();

    /**
     * Get the object on the top of the stack and <b>remove</b> it from the stack.
     *
     * @return the object on the top of the stack
     * @see CompoundRoot#pop()
     */
    public abstract Object pop();

    /**
     * Put this object onto the top of the stack
     *
     * @param o the object to be pushed onto the stack
     * @see CompoundRoot#push(Object)
     */
    public abstract void push(Object o);

    /**
     * Sets an object on the stack with the given key
     * so it is retrievable by {@link #findValue(String)}, {@link #findValue(String, Class)}
     *
     * @param key  the key
     * @param o    the object
     */
    public abstract void set(String key, Object o);

    /**
     * Get the number of objects in the stack
     *
     * @return the number of objects in the stack
     */
    public abstract int size();

}