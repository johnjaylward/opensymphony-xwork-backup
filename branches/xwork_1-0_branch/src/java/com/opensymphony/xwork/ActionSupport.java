/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork;

import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.*;


/**
 * Provides a default implementation for the most common actions.
 * See the documentation for all the interfaces this class implements for more detailed information.
 */
public class ActionSupport implements Action, Validateable, ValidationAware, TextProvider, LocaleProvider, Serializable {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected transient static final Log LOG = LogFactory.getLog(ActionSupport.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    private transient final TextProvider textProvider = new TextProviderSupport(getClass(), this);
    private final ValidationAware validationAware = new ValidationAwareSupport();

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setActionErrors(Collection errorMessages) {
        validationAware.setActionErrors(errorMessages);
    }

    public Collection getActionErrors() {
        return validationAware.getActionErrors();
    }

    public void setActionMessages(Collection messages) {
        validationAware.setActionMessages(messages);
    }

    public Collection getActionMessages() {
        return validationAware.getActionMessages();
    }

    /**
     * @deprecated Use {@link #getActionErrors()}.
     */
    public Collection getErrorMessages() {
        return getActionErrors();
    }

    /**
     * @deprecated Use {@link #getFieldErrors()}.
     */
    public Map getErrors() {
        return getFieldErrors();
    }

    public void setFieldErrors(Map errorMap) {
        validationAware.setFieldErrors(errorMap);
    }

    /**
     * @return a Map of field names -> List of errors or null if no errors have been added
     */
    public Map getFieldErrors() {
        return validationAware.getFieldErrors();
    }

    public Locale getLocale() {
        return (ActionContext.getContext() != null) ? ActionContext.getContext().getLocale() : null;
    }

    public String getText(String aTextName) {
        return textProvider.getText(aTextName);
    }

    public String getText(String aTextName, String defaultValue) {
        return textProvider.getText(aTextName, defaultValue);
    }

    public String getText(String aTextName, List args) {
        return textProvider.getText(aTextName, args);
    }

    public String getText(String aTextName, String defaultValue, List args) {
        return textProvider.getText(aTextName, defaultValue, args);
    }

    public ResourceBundle getTexts(String aBundleName) {
        return textProvider.getTexts(aBundleName);
    }

    public String getText(String key, String defaultValue, List args, OgnlValueStack stack) {
        return textProvider.getText(key,defaultValue,args,stack);
    }

    public ResourceBundle getTexts() {
        return textProvider.getTexts();
    }

    public void addActionError(String anErrorMessage) {
        validationAware.addActionError(anErrorMessage);
    }

    public void addActionMessage(String aMessage) {
        validationAware.addActionMessage(aMessage);
    }

    public void addFieldError(String fieldName, String errorMessage) {
        validationAware.addFieldError(fieldName, errorMessage);
    }

    public String doDefault() throws Exception {
        return SUCCESS;
    }

    /**
     * A default implementation that does nothing an returns "success".
     *
     * @return {@link #SUCCESS}
     */
    public String execute() throws Exception {
        return SUCCESS;
    }

    public boolean hasActionErrors() {
        return validationAware.hasActionErrors();
    }

    /**
     * Checks whether there are any Action-level messages.
     *
     * @return true if any Action-level messages have been registered
     */
    public boolean hasActionMessages() {
        return validationAware.hasActionMessages();
    }

    public boolean hasErrors() {
        return validationAware.hasErrors();
    }

    public boolean hasFieldErrors() {
        return validationAware.hasFieldErrors();
    }

    /**
     * A default implementation that validates nothing.
     * Subclasses should override this method to provide validations.
     */
    public void validate() {
    }
}