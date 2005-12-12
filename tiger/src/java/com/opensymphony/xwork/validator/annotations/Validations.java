/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.validator.annotations;

import com.opensymphony.xwork.validator.annotations.CustomValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
 * <table>
 * <thead>
 * <tr>
 * <th>Parameter</th>
 * <th>Required</th>
 * <th>Default</th>
 * <th>Description</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>param</td>
 * <td>required</td>
 * <td>default</td>
 * <td>description</td>
 * </tr>
 * </tbody>
 * </table>
 * <!-- END SNIPPET: parameters -->
 *
 * <p/> <u>Example code:</u>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author jepjep
 * @author Rainer Hermanns
 * @version $Id$
 */
@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validations {

    /**
     * Custom Validation rules.
     */
    public CustomValidator[] customValidators() default {};

    public ConversionErrorFieldValidator[] conversionErrorFields() default {};

    public DateRangeFieldValidator[] dateRangeFields() default {};

    public EmailValidator[] emails() default {};

    public FieldExpressionValidator[] fieldExpressions() default {};

    public IntRangeFieldValidator[] intRangeFields() default {};

    public RequiredFieldValidator[] requiredFields() default {};

    public RequiredStringValidator[] requiredStrings() default {};

    public StringLengthFieldValidator[] stringLengthFields() default {};

    public UrlValidator[] urls() default {};
    
    public VisitorFieldValidator[] visitorFields() default {};

    public StringRegexValidator[] stringRegexs() default {};

    public RegexFieldValidator[] regexFields() default {};

    public ExpressionValidator[] expressions() default {};
}
