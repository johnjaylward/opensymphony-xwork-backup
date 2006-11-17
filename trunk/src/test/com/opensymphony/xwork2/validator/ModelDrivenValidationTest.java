/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork2.validator;

import com.opensymphony.xwork2.*;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ModelDrivenValidationTest
 *
 * @author Jason Carreira
 *         Created Oct 1, 2003 10:08:25 AM
 */
public class ModelDrivenValidationTest extends XWorkTestCase {

    public void testModelDrivenValidation() throws Exception {
        Map params = new HashMap();
        params.put("count", new String[]{"11"});

        Map context = new HashMap();
        context.put(ActionContext.PARAMETERS, params);

        configurationManager.addConfigurationProvider(new XmlConfigurationProvider("xwork-sample.xml"));
        ActionProxy proxy = container.getInstance(ActionProxyFactory.class).createActionProxy(
                configurationManager.getConfiguration(), null, "TestModelDrivenValidation", context);
        assertEquals(Action.SUCCESS, proxy.execute());

        ModelDrivenAction action = (ModelDrivenAction) proxy.getAction();
        assertTrue(action.hasFieldErrors());
        assertTrue(action.getFieldErrors().containsKey("count"));
        assertEquals("count must be between 1 and 10, current value is 11.", ((List) action.getFieldErrors().get("count")).get(0));
    }

}
