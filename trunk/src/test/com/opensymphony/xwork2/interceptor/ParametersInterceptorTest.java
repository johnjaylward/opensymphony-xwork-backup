/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork2.interceptor;

import com.opensymphony.xwork2.*;
import com.opensymphony.xwork2.config.providers.MockConfigurationProvider;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.opensymphony.xwork2.util.OgnlValueStack;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;


/**
 * Unit test for {@link ParametersInterceptor}.
 *
 * @author Jason Carreira
 */
public class ParametersInterceptorTest extends XWorkTestCase {

    public void testParameterNameAware() {
        ParametersInterceptor pi = new ParametersInterceptor();
        final Map actual = new HashMap();
        OgnlValueStack stack = new OgnlValueStack() {
            public void setValue(String expr, Object value) {
                actual.put(expr, value);
            }
        };
        final Map expected = new HashMap() {
            {
                put("fooKey", "fooValue");
                put("barKey", "barValue");
            }
        };
        Object a = new ParameterNameAware() {
            public boolean acceptableParameterName(String parameterName) {
                return expected.containsKey(parameterName);
            }
        };
        Map parameters = new HashMap() {
            {
                put("fooKey", "fooValue");
                put("barKey", "barValue");
                put("error", "error");
            }
        };
        pi.setParameters(a, stack, parameters);
        assertEquals(expected, actual);
    }

    public void testDoesNotAllowMethodInvocations() throws Exception {
        Map params = new HashMap();
        params.put("@java.lang.System@exit(1).dummy", "dumb value");

        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, params);

        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
                configurationManager.getConfiguration(), "", MockConfigurationProvider.MODEL_DRIVEN_PARAM_TEST, extraContext);
        assertEquals(Action.SUCCESS, proxy.execute());

        ModelDrivenAction action = (ModelDrivenAction) proxy.getAction();
        TestBean model = (TestBean) action.getModel();

        String property = System.getProperty("webwork.security.test");
        assertNull(property);
    }

    public void testModelDrivenParameters() throws Exception {
        Map params = new HashMap();
        final String fooVal = "com.opensymphony.xwork2.interceptor.ParametersInterceptorTest.foo";
        params.put("foo", fooVal);

        final String nameVal = "com.opensymphony.xwork2.interceptor.ParametersInterceptorTest.name";
        params.put("name", nameVal);
        params.put("count", "15");

        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, params);

        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
                configurationManager.getConfiguration(), "", MockConfigurationProvider.MODEL_DRIVEN_PARAM_TEST, extraContext);
        assertEquals(Action.SUCCESS, proxy.execute());

        ModelDrivenAction action = (ModelDrivenAction) proxy.getAction();
        TestBean model = (TestBean) action.getModel();
        assertEquals(nameVal, model.getName());
        assertEquals(15, model.getCount());
        assertEquals(fooVal, action.getFoo());
    }

    public void testParametersDoesNotAffectSession() throws Exception {
        Map params = new HashMap();
        params.put("blah", "This is blah");
        params.put("#session.foo", "Foo");

        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, params);

        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
                configurationManager.getConfiguration(), "", MockConfigurationProvider.PARAM_INTERCEPTOR_ACTION_NAME, extraContext);
        OgnlValueStack stack = proxy.getInvocation().getStack();
        HashMap session = new HashMap();
        stack.getContext().put("session", session);
        proxy.execute();
        assertEquals("This is blah", ((SimpleAction) proxy.getAction()).getBlah());
        assertNull(session.get("foo"));
    }

    public void testParameters() throws Exception {
        Map params = new HashMap();
        params.put("blah", "This is blah");

        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, params);

        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
                configurationManager.getConfiguration(), "", MockConfigurationProvider.PARAM_INTERCEPTOR_ACTION_NAME, extraContext);
        proxy.execute();
        assertEquals("This is blah", ((SimpleAction) proxy.getAction()).getBlah());
    }

    public void testNonexistentParametersGetLoggedInDevMode() throws Exception {
        Map params = new HashMap();
        params.put("not_a_property", "There is no action property named like this");

        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, params);
        extraContext.put(ActionContext.DEV_MODE, Boolean.TRUE);

        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
                configurationManager.getConfiguration(), "", MockConfigurationProvider.PARAM_INTERCEPTOR_ACTION_NAME, extraContext);
        proxy.execute();
        final String actionMessage = "" + ((SimpleAction) proxy.getAction()).getActionMessages().toArray()[0];
        assertTrue(actionMessage.indexOf("No object in the CompoundRoot has a publicly accessible property named 'not_a_property' (no setter could be found).") > -1);
    }

    public void testNonexistentParametersAreIgnoredInProductionMode() throws Exception {
        Map params = new HashMap();
        params.put("not_a_property", "There is no action property named like this");

        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, params);
        extraContext.put(ActionContext.DEV_MODE, Boolean.FALSE);

        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
                configurationManager.getConfiguration(), "", MockConfigurationProvider.PARAM_INTERCEPTOR_ACTION_NAME, extraContext);
        proxy.execute();
        assertTrue(((SimpleAction) proxy.getAction()).getActionMessages().isEmpty());
    }

    public void testNoParametersAction() throws Exception {
        ParametersInterceptor interceptor = new ParametersInterceptor();
        interceptor.init();

        MockActionInvocation mai = new MockActionInvocation();
        Action action = new NoParametersAction();
        mai.setAction(action);

        interceptor.intercept(mai);
        interceptor.destroy();
    }

    private class NoParametersAction implements Action, NoParameters {

        public String execute() throws Exception {
            return SUCCESS;
        }
    }

    protected void setUp() throws Exception {
        configurationManager.clearConfigurationProviders();
        configurationManager.addConfigurationProvider(new MockConfigurationProvider());
        configurationManager.reload();
    }
}