/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.util;


/**
 *
 *
 * @author <a href="mailto:plightbo@cisco.com">Pat Lightbody</a>
 * @author $Author$
 * @version $Revision$
 */
public class Bar {
    //~ Instance fields ////////////////////////////////////////////////////////

    String title;
    int somethingElse;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setSomethingElse(int somethingElse) {
        this.somethingElse = somethingElse;
    }

    public int getSomethingElse() {
        return somethingElse;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
