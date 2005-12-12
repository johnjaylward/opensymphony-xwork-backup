/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.validator.annotations;

import java.lang.annotation.*;

/**
 * <!-- START SNIPPET: description -->
 * <!-- END SNIPPET: description -->
 *
 * <p/> <u>Annotation usage:</u>
 *
 * <!-- START SNIPPET: usage -->
 *
 * <!-- END SNIPPET: usage -->
 *
 * <p/> <u>Annotation parameters:</u>
 *
 * <!-- START SNIPPET: parameters -->
 * <table class='confluenceTable'>
 * <tr>
 * <th class='confluenceTh'> Parameter </th>
 * <th class='confluenceTh'> Required </th>
 * <th class='confluenceTh'> Default </th>
 * <th class='confluenceTh'> Notes </th>
 * </tr>
 * <tr>
 * <td class='confluenceTd'> regex</td>
 * <td class='confluenceTd'> yes </td>
 * <td class='confluenceTd'> "." </td>
 * <td class='confluenceTd'> String property.  The Regular Expression for which to check a match.  </td>
 * </tr>
 * <tr>
 * <td class='confluenceTd'> caseSensitive</td>
 * <td class='confluenceTd'> no </td>
 * <td class='confluenceTd'> true </td>
 * <td class='confluenceTd'> Whether the matching of alpha characters in the expression should be done case-sensitively. </td>
 * </tr>
 * </table>
 * <!-- END SNIPPET: parameters -->
 *
 * <p/> <u>Example code:</u>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * @StringRegexValidator(message = "Default message", key = "i18n.key", shortCircuit = true, regex = "a regular expression", caseSensitive = true)
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id$
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringRegexValidator {

    /**
     * The aliasNames are the names of the Action aliases as defined in the xwork.xml configuration for the particular Action.
     */
    String[] aliasNames() default {};

    /**
     * The default error message for this validator.
     */
    String message();

    /**
     * The message key to lookup for i18n.
     */
    String key() default "";

    /**
     * The optional fieldName for SIMPLE validator types.
     */
    String fieldName() default "";

    /**
     * String property. The Regular ExpressionValidator for which to check a match.
     */
    String regex();

    /**
     * Whether the matching of alpha characters in the expression should be done case-sensitively.
     */
    boolean caseSensitive() default true;

    /**
     * If this is activated, the validator will be used as short-circuit.
     *
     * Adds the short-circuit="true" attribute value if <tt>true</tt>.
     *
     */
    boolean shortCircuit() default false;

    /**
     * The validation type for this field/method.
     */
    ValidatorType type() default ValidatorType.FIELD;
}