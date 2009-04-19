/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork2.config.entities;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.io.Serializable;

import com.opensymphony.xwork2.util.location.Located;
import com.opensymphony.xwork2.util.location.Location;


/**
 * Configuration class for result types.
 * <p/>
 * In the xml configuration file this is defined as the <code>result-type</code> tag.
 *
 * @author Mike
 * @author Rainer Hermanns
 * @author Neo
 */
public class ResultTypeConfig extends Located implements Serializable {

    private String className;
    private String name;
    private String defaultResultParam;

    private Map<String,String> params;

    protected ResultTypeConfig(String name, String className) {
        this.name = name;
        this.className = className;
        params = new LinkedHashMap<String,String>();
    }

    protected ResultTypeConfig(ResultTypeConfig orig) {
        this.name = orig.name;
        this.className = orig.className;
        this.defaultResultParam = orig.defaultResultParam;
        this.params = orig.params;
    }


    public void setDefaultResultParam(String defaultResultParam) {
    	this.defaultResultParam = defaultResultParam;
    }
    
    public String getDefaultResultParam() {
    	return this.defaultResultParam;
    }

    /**
     * @deprecated Since 2.1, use {@link #getClassName()} instead
     */
    public String getClazz() {
        return className;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public Map<String,String> getParams() {
        return this.params;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ResultTypeConfig that = (ResultTypeConfig) o;

        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (params != null ? !params.equals(that.params) : that.params != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (className != null ? className.hashCode() : 0);
        result = 29 * result + (name != null ? name.hashCode() : 0);
        result = 29 * result + (params != null ? params.hashCode() : 0);
        return result;
    }

    /**
     * The builder for this object.  An instance of this object is the only way to construct a new instance.  The
     * purpose is to enforce the immutability of the object.  The methods are structured in a way to support chaining.
     * After setting any values you need, call the {@link #build()} method to create the object.
     */
    public static final class Builder {
        private ResultTypeConfig target;

        public Builder(String name, String className) {
            target = new ResultTypeConfig(name, className);
        }

        public Builder(ResultTypeConfig orig) {
            target = new ResultTypeConfig(orig);
        }

        public Builder name(String name) {
            target.name = name;
            return this;
        }

        public Builder className(String name) {
            target.className = name;
            return this;
        }

         public Builder addParam(String name, String value) {
            target.params.put(name, value);
            return this;
        }

        public Builder addParams(Map<String,String> params) {
            target.params.putAll(params);
            return this;
        }

        public Builder defaultResultParam(String defaultResultParam) {
            target.defaultResultParam = defaultResultParam;
            return this;
        }

        public Builder location(Location loc) {
            target.location = loc;
            return this;
        }

        public ResultTypeConfig build() {
            target.params = Collections.unmodifiableMap(target.params);
            ResultTypeConfig result = target;
            target = new ResultTypeConfig(target);
            return result;
        }
    }
}