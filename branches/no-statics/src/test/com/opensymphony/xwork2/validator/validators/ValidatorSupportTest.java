/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork2.validator.validators;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.OgnlValueStack;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.ValidatorSupport;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class ValidatorSupportTest extends TestCase {

	public void testConditionalParseExpression()  throws Exception {
		ValueStack oldStack = ActionContext.getContext().getValueStack();
		try {
			OgnlValueStack stack = new OgnlValueStack();
			stack.getContext().put("something", "somevalue");
			ActionContext.getContext().setValueStack(stack);
			ValidatorSupport validator = new ValidatorSupport() {
				public void validate(Object object) throws ValidationException {
				}
			};

			validator.setParse(true);
			String result1 = validator.conditionalParse("${#something}").toString();

			validator.setParse(false);
			String result2 = validator.conditionalParse("${#something}").toString();

			assertEquals(result1, "somevalue");
			assertEquals(result2, "${#something}");
		}
		finally {
			ActionContext.getContext().setValueStack(oldStack);
		}
	}

}
