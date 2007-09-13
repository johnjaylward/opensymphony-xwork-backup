/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork2.util;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.ognl.OgnlReflectionProvider;
import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.opensymphony.xwork2.util.location.LocatableProperties;

/**
 * Generic test setup methods to be used with any unit testing framework. 
 */
public class XWorkTestCaseHelper {

    public static ConfigurationManager setUp() throws Exception {
        ConfigurationManager configurationManager = new ConfigurationManager();
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();
        
        // Reset the value stack
        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));
    
        // clear out localization
        LocalizedTextUtil.reset();
        
    
        //ObjectFactory.setObjectFactory(container.getInstance(ObjectFactory.class));
        return configurationManager;
    }

    public static ConfigurationManager loadConfigurationProviders(ConfigurationManager configurationManager,
            ConfigurationProvider... providers) {
        if (configurationManager != null) {
            configurationManager.clearConfigurationProviders();
        } else {
            configurationManager = new ConfigurationManager();
        }
        configurationManager.clearConfigurationProviders();
        configurationManager.addConfigurationProvider(new ConfigurationProvider() {
            public void destroy() {}
            public void init(Configuration configuration) throws ConfigurationException {}
            public void loadPackages() throws ConfigurationException {}
            public boolean needsReload() { return false; }

            public void register(ContainerBuilder builder,
                    LocatableProperties props) throws ConfigurationException {
                builder.setAllowDuplicates(true);
            }
            
        });
        configurationManager.addConfigurationProvider(new XWorkConfigurationProvider());
        for (ConfigurationProvider prov : providers) {
            if (prov instanceof XmlConfigurationProvider) {
                ((XmlConfigurationProvider)prov).setThrowExceptionOnDuplicateBeans(false);
            }
            configurationManager.addConfigurationProvider(prov);
        }
        configurationManager.getConfiguration().reload(
                configurationManager.getConfigurationProviders());
        Container container = configurationManager.getConfiguration().getContainer();
        
        // Reset the value stack
        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));
        
        return configurationManager;
    }

    public static void tearDown(ConfigurationManager configurationManager) throws Exception {
    
        //  clear out configuration
        if (configurationManager != null) {
            configurationManager.destroyConfiguration();
            configurationManager = null;
        }
        ActionContext.setContext(null);
    }
}