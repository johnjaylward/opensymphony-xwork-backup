/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork2.ognl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.ognl.accessor.CompoundRootAccessor;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

/**
 * Ognl implementation of a value stack that allows for dynamic Ognl expressions to be evaluated against it. When
 * evaluating an expression, the stack will be searched down the stack, from the latest objects pushed in to the
 * earliest, looking for a bean with a getter or setter for the given property or a method of the given name (depending
 * on the expression being evaluated).
 *
 * @author Patrick Lightbody
 * @author tm_jee
 * 
 * @version $Date$ $Id$
 */
public class OgnlValueStack implements Serializable, ValueStack {
	
	private static final long serialVersionUID = 370737852934925530L;
	
    private static Log LOG = LogFactory.getLog(OgnlValueStack.class);
    private boolean devMode;
    private boolean allowStaticMethodAccess = true;

    /**
     * @depreated Since 2.1
     */
    public static void reset() {
        // create a new OgnlValueStackFactory instead
    }

    public static void link(Map context, Class clazz, String name) {
        context.put("__link", new Object[]{clazz, name});
    }


    CompoundRoot root;
    transient Map context;
    Class defaultType;
    Map overrides;
    OgnlUtil ognlUtil;
    
    protected OgnlValueStack(XWorkConverter xworkConverter, CompoundRootAccessor accessor, TextProvider prov) {
        setRoot(xworkConverter, accessor, new CompoundRoot());
        push(prov);
    }


    protected OgnlValueStack(ValueStack vs, XWorkConverter xworkConverter, CompoundRootAccessor accessor) {
        setRoot(xworkConverter, accessor, new CompoundRoot(vs.getRoot()));
    }
    
    @Inject
    public void setOgnlUtil(OgnlUtil ognlUtil) {
        this.ognlUtil = ognlUtil;
    }

    protected void setRoot(XWorkConverter xworkConverter,
            CompoundRootAccessor accessor, CompoundRoot compoundRoot) {
        this.root = compoundRoot;
        this.context = Ognl.createDefaultContext(this.root, accessor, new OgnlTypeConverterWrapper(xworkConverter),
                new StaticMemberAccess(allowStaticMethodAccess));
        context.put(VALUE_STACK, this);
        Ognl.setClassResolver(context, accessor);
        ((OgnlContext) context).setTraceEvaluations(false);
        ((OgnlContext) context).setKeepLastEvaluation(false);
    }
    
    @Inject("devMode")
    public void setDevMode(String mode) {
        devMode = "true".equalsIgnoreCase(mode);
    }

    @Inject(value="allowStaticMethodAccess", required=false)
    public void setAllowStaticMethodAccess(String allowStaticMethodAccess) {
        this.allowStaticMethodAccess = "true".equalsIgnoreCase(allowStaticMethodAccess);
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#getContext()
     */
    public Map getContext() {
        return context;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#setDefaultType(java.lang.Class)
     */
    public void setDefaultType(Class defaultType) {
        this.defaultType = defaultType;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#setExprOverrides(java.util.Map)
     */
    public void setExprOverrides(Map overrides) {
    	if (this.overrides == null) {
    		this.overrides = overrides;
    	}
    	else {
    		this.overrides.putAll(overrides);
    	}
    }
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#getExprOverrides()
     */
    public Map getExprOverrides() {
    	return this.overrides;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#getRoot()
     */
    public CompoundRoot getRoot() {
        return root;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#setValue(java.lang.String, java.lang.Object)
     */
    public void setValue(String expr, Object value) {
        setValue(expr, value, devMode);
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#setValue(java.lang.String, java.lang.Object, boolean)
     */
    public void setValue(String expr, Object value, boolean throwExceptionOnFailure) {
        Map context = getContext();

        try {
            context.put(XWorkConverter.CONVERSION_PROPERTY_FULLNAME, expr);
            context.put(REPORT_ERRORS_ON_NO_PROP, (throwExceptionOnFailure) ? Boolean.TRUE : Boolean.FALSE);
            ognlUtil.setValue(expr, context, root, value);
        } catch (OgnlException e) {
            if (throwExceptionOnFailure) {
                e.printStackTrace(System.out);
                System.out.println("expr: "+expr+" val: "+value+" context: "+context+" root:"+root+" value: "+value);
                String msg = "Error setting expression '" + expr + "' with value '" + value + "'";
                throw new XWorkException(msg, e);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error setting value", e);
                }
            }
        } catch (RuntimeException re) { //XW-281
            if (throwExceptionOnFailure) {
                StringBuffer msg = new StringBuffer();
                msg.append("Error setting expression '");
                msg.append(expr);
                msg.append("' with value ");

                if (value instanceof Object[]) {
                    Object[] valueArray = (Object[]) value;
                    msg.append("[");
                    for (int index = 0; index < valueArray.length; index++) {
                        msg.append("'");
                        msg.append(valueArray[index]);
                        msg.append("'");

                        if (index < (valueArray.length + 1))
                            msg.append(", ");
                    }
                    msg.append("]");
                } else {
                    msg.append("'");
                    msg.append(value);
                    msg.append("'");
                }

                throw new XWorkException(msg.toString(), re);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error setting value", re);
                }
            }
        } finally {
            ReflectionContextState.clear(context);
            context.remove(XWorkConverter.CONVERSION_PROPERTY_FULLNAME);
            context.remove(REPORT_ERRORS_ON_NO_PROP);
        }
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#findString(java.lang.String)
     */
    public String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#findValue(java.lang.String)
     */
    public Object findValue(String expr) {
        try {
            if (expr == null) {
                return null;
            }

            if ((overrides != null) && overrides.containsKey(expr)) {
                expr = (String) overrides.get(expr);
            }

            if (defaultType != null) {
                return findValue(expr, defaultType);
            }

            Object value = ognlUtil.getValue(expr, context, root);
            if (value != null) {
                return value;
            } else {
                return findInContext(expr);
            }
        } catch (OgnlException e) {
            return findInContext(expr);
        } catch (Exception e) {
            logLookupFailure(expr, e);

            return findInContext(expr);
        } finally {
            ReflectionContextState.clear(context);
        }
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#findValue(java.lang.String, java.lang.Class)
     */
    public Object findValue(String expr, Class asType) {
        try {
            if (expr == null) {
                return null;
            }

            if ((overrides != null) && overrides.containsKey(expr)) {
                expr = (String) overrides.get(expr);
            }

            Object value = ognlUtil.getValue(expr, context, root, asType);
            if (value != null) {
                return value;
            } else {
                return findInContext(expr);
            }
        } catch (OgnlException e) {
            return findInContext(expr);
        } catch (Exception e) {
            logLookupFailure(expr, e);

            return findInContext(expr);
        } finally {
            ReflectionContextState.clear(context);
        }
    }

    private Object findInContext(String name) {
        return getContext().get(name);
    }

    /**
     * Log a failed lookup, being more verbose when devMode=true.
     *
     * @param expr The failed expression
     * @param e    The thrown exception.
     */
    private void logLookupFailure(String expr, Exception e) {
        StringBuffer msg = new StringBuffer();
        msg.append("Caught an exception while evaluating expression '").append(expr).append("' against value stack");
        if (devMode && LOG.isWarnEnabled()) {
            LOG.warn(msg, e);
            LOG.warn("NOTE: Previous warning message was issued due to devMode set to true.");
        } else if (LOG.isDebugEnabled()) {
            LOG.debug(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#peek()
     */
    public Object peek() {
        return root.peek();
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#pop()
     */
    public Object pop() {
        return root.pop();
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#push(java.lang.Object)
     */
    public void push(Object o) {
        root.push(o);
    }
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#set(java.lang.String, java.lang.Object)
     */
    public void set(String key, Object o) {
    	//set basically is backed by a Map
    	//pushed on the stack with a key 
    	//being put on the map and the 
    	//Object being the value
    	
    	Map setMap=null;
    	
    	//check if this is a Map 
    	//put on the stack  for setting
    	//if so just use the old map (reduces waste)
    	Object topObj=peek();
    	if (topObj instanceof Map 
    			&&((Map)topObj).get(MAP_IDENTIFIER_KEY)!=null) {
    		
    		setMap=(Map)topObj;
    	}	else {
    		setMap=new HashMap();
    		//the map identifier key ensures
    		//that this map was put there
    		//for set purposes and not by a user
    		//whose data we don't want to touch
    		setMap.put(MAP_IDENTIFIER_KEY,"");
    		push(setMap);
    	}
    	setMap.put(key,o);
    	
    }
    
    
    private static final String MAP_IDENTIFIER_KEY="com.opensymphony.xwork2.util.OgnlValueStack.MAP_IDENTIFIER_KEY";
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.util.ValueStack#size()
     */
    public int size() {
        return root.size();
    }
 
}