/*
 * Copyright (c) 2006, Your Corporation. All Rights Reserved.
 */

package com.opensymphony.xwork.validator.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <!-- START SNIPPET: description -->
 * This validator checks that a double field has a value within a specified range.
 * If neither min nor max is set, nothing will be done.
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
 * <td class='confluenceTd'>message</td>
 * <td class='confluenceTd'>yes</td>
 * <td class='confluenceTd'>&nbsp;</td>
 * <td class='confluenceTd'>field error message</td>
 * </tr>
 * <tr>
 * <td class='confluenceTd'>key</td>
 * <td class='confluenceTd'>no</td>
 * <td class='confluenceTd'>&nbsp;</td>
 * <td class='confluenceTd'>i18n key from language specific properties file.</td>
 * </tr>
 * <tr>
 * <td class='confluenceTd'>fieldName</td>
 * <td class='confluenceTd'>no</td>
 * <td class='confluenceTd'>&nbsp;</td>
 * <td class='confluenceTd'>&nbsp;</td>
 * </tr>
 * <tr>
 * <td class='confluenceTd'>shortCircuit</td>
 * <td class='confluenceTd'>no</td>
 * <td class='confluenceTd'>false</td>
 * <td class='confluenceTd'>If this validator should be used as shortCircuit.</td>
 * </tr>
 * <tr>
 * <td class='confluenceTd'>type</td>
 * <td class='confluenceTd'>yes</td>
 * <td class='confluenceTd'>ValidatorType.FIELD</td>
 * <td class='confluenceTd'>Enum value from ValidatorType. Either FIELD or SIMPLE can be used here.</td>
 * </tr>
 * <tr>
 * <td class='confluenceTd'> min </td>
 * <td class='confluenceTd'> no </td>
 * <td class='confluenceTd'>&nbsp;</td>
 * <td class='confluenceTd'> Double property.  The minimum the number must be. </td>
 * </tr>
 * <tr>
 * <td class='confluenceTd'> max </td>
 * <td class='confluenceTd'> no </td>
 * <td class='confluenceTd'>&nbsp;</td>
 * <td class='confluenceTd'> Double property.  The maximum number can be. </td>
 * </tr>
 * </table>
 *
 * <p>If neither <em>min</em> nor <em>max</em> is set, nothing will be done.</p>
 *
 * <p>The values for min and max must be inserted as String values so that "0" can be handled as a possible value.</p>
 * <!-- END SNIPPET: parameters -->
 *
 * <p/> <u>Example code:</u>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * @DoubleRangeFieldValidator(message = "Default message", key = "i18n.key", shortCircuit = true, min = "0.123", max = "99.987")
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id$
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleRangeFieldValidator {

    /**
     *  Double property. The minimum the number must be.
     */
    String min() default "";

    /**
     *  Double property. The maximum number can be.
     */
    String max() default "";

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
