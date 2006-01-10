/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork;

import com.opensymphony.xwork.util.LocalizedTextUtil;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * LocaleAwareSupport
 * @author Jason Carreira
 * Created Aug 3, 2003 12:21:12 AM
 */
public class LocaleAwareSupport implements LocaleAware {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Class clazz;

    //~ Constructors ///////////////////////////////////////////////////////////

    public LocaleAwareSupport(Class clazz) {
        this.clazz = clazz;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Get the locale for this action.
    *
    * Applications may customize how locale is chosen by
    * subclassing ActionSupport and override this methodName.
    *
    * @return     the locale to use
    */
    public Locale getLocale() {
        return ActionContext.getContext().getLocale();
    }

    /**
    * Get a text from the resource bundles associated with this action.
    * The resource bundles are searched, starting with the one associated
    * with this particular action, and testing all its superclasses' bundles.
    * It will stop once a bundle is found that contains the given text. This gives
    * a cascading style that allow global texts to be defined for an application base
    * class.
    *
    * @param   aTextName  name of text to be found
    * @return     value of named text
    */
    public String getText(String aTextName) {
        return LocalizedTextUtil.findText(clazz, aTextName);
    }

    /**
     * Get a text from the resource bundles associated with this action.
     * The resource bundles are searched, starting with the one associated
     * with this particular action, and testing all its superclasses' bundles.
     * It will stop once a bundle is found that contains the given text. This gives
     * a cascading style that allow global texts to be defined for an application base
     * class. If no text is found for this text name, the default value is returned.
     *
     * @param   aTextName  name of text to be found
     * @param   defaultValue the default value which will be returned if no text is found
     * @return     value of named text
     */
    public String getText(String aTextName, String defaultValue) {
        return LocalizedTextUtil.findText(clazz, aTextName, getLocale(), defaultValue);
    }

    /**
    * Get the named bundle.
    *
    * You can override the getLocale() methodName to change the behaviour of how
    * to choose locale for the bundles that are returned. Typically you would
    * use the LocaleAware interface to get the users configured locale, or use
    * your own methodName to allow the user to select the locale and store it in
    * the session (by using the SessionAware interface).
    *
    * @param   aBundleName  bundle name
    * @return     a resource bundle
    */
    public ResourceBundle getTexts(String aBundleName) {
        return LocalizedTextUtil.findResourceBundle(aBundleName, getLocale());
    }

    /**
    * Get the resource bundle associated with this action.
    * This will be based on the actual subclass that is used.
    *
    * @return     resouce bundle
    */
    public ResourceBundle getTexts() {
        return getTexts(clazz.getName());
    }
}