/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.interceptor;

import com.mockobjects.dynamic.Mock;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Validateable;
import com.opensymphony.xwork.ValidationAware;

import junit.framework.TestCase;


/**
 * DefaultWorkflowInterceptorTest
 * @author Jason Carreira
 * Created Aug 29, 2003 1:51:00 PM
 */
public class DefaultWorkflowInterceptorTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    DefaultWorkflowInterceptor interceptor;
    private ActionInvocation invocation;
    private Mock actionMock;
    private Mock invocationMock;
    private ValidateAction action;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testInvokesActionInvocationIfNoErrors() throws Exception {
        actionMock.expectAndReturn("hasErrors", false);

        final String result = "testing123";
        invocationMock.expectAndReturn("invoke", result);
        assertEquals(result, interceptor.intercept(invocation));
    }

    public void testReturnsInputWithoutExecutingIfHasErrors() throws Exception {
        actionMock.expectAndReturn("hasErrors", true);
        assertEquals(Action.INPUT, interceptor.intercept(invocation));
    }

    protected void setUp() throws Exception {
        super.setUp();
        actionMock = new Mock(ValidateAction.class);
        actionMock.expect("validate");
        action = (ValidateAction) actionMock.proxy();
        invocationMock = new Mock(ActionInvocation.class);
        invocationMock.expectAndReturn("getAction", action);
        invocation = (ActionInvocation) invocationMock.proxy();
        interceptor = new DefaultWorkflowInterceptor();
    }

    //~ Inner Interfaces ///////////////////////////////////////////////////////

    private interface ValidateAction extends Action, Validateable, ValidationAware {
    }
}
