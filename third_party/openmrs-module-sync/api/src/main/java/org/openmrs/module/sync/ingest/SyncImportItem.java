/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.sync.ingest;

import org.openmrs.module.sync.SyncItemKey;
import org.openmrs.module.sync.SyncItemState;
import org.openmrs.module.sync.serialization.Item;
import org.openmrs.module.sync.serialization.Record;

/**
 *
 */
public class SyncImportItem {

    //private Log log = LogFactory.getLog(this.getClass());

    // Fields
    private SyncItemKey<?> key = null;
    private SyncItemState state = SyncItemState.UNKNOWN;
    private String errorMessage = "";
    private String errorMessageArgs = "";
    private String errorMessageDetail = ""; //usually stack trace

    public String getErrorMessageDetail() {
    	return errorMessageDetail;
    }

	public void setErrorMessageDetail(String detail) {
    	this.errorMessageDetail = detail;
    }

    public String getErrorMessage() {
    	return errorMessage;
    }

	public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }

    public SyncItemKey<?> getKey() {
        return key;
    }
    
    public void setKey(SyncItemKey<?> key) {
        this.key = key;
    }

    public String getErrorMessageArgs() {
    	return errorMessageArgs;
    }

	public void setErrorMessageArgs(String errorMessageArgs) {
    	this.errorMessageArgs = errorMessageArgs;
    }

	public SyncItemState getState() {
        return state;
    }
    
    public void setState(SyncItemState state) {
        this.state = state;
    }
 
    // Methods
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SyncImportItem) || o == null)
            return false;

        SyncImportItem oSync = (SyncImportItem) o;
        boolean same = ((oSync.getKey() == null) ? (this.getKey() == null) : oSync.getKey().equals(this.getKey()))
                && ((oSync.getErrorMessage() == null) ? (this.getErrorMessage() == null) : oSync.getErrorMessage().equals(this.getErrorMessage()))
                && ((oSync.getErrorMessageArgs() == null) ? (this.getErrorMessageArgs() == null) : oSync.getErrorMessageArgs().equals(this.getErrorMessageArgs()))
                && ((oSync.getErrorMessageDetail() == null) ? (this.getErrorMessageDetail() == null) : oSync.getErrorMessageDetail().equals(this.getErrorMessageDetail()))
                && ((oSync.getState() == null) ? (this.getState() == null) : oSync.getState().equals(this.getState()));
      
        return same;
    }

    @Override
    public int hashCode() {
        //FIXME: Key might be null, though it shouldn't..
        // Should these key-objects implement some interface - causes problems with serialization.
        if (getKey() != null) {
            return getKey().hashCode();
        } else {
            return super.hashCode();
        }
    }

    public Item save(Record xml, Item parent) throws Exception {
        Item me = xml.createItem(parent, this.getClass().getSimpleName());

        //serialize primitives
        xml.setAttribute(me, "state", state.toString());
        if(errorMessage != null) xml.setAttribute(me, "errorMessage", errorMessage);
        if(errorMessageArgs != null) xml.setAttribute(me, "errorMessageArgs", errorMessageArgs.toString());

        if (key != null) {
        	xml.setAttribute(me,"key",key.getKeyValue().toString());
        }
        
        Item itemErrorMessageDetail = xml.createItem(me, "errorMessageDetail");
        if (errorMessageDetail != null) {
            xml.createTextAsCDATA(itemErrorMessageDetail, errorMessageDetail);
        }
        
        return me;
    }
    
    public void load(Record xml, Item me) throws Exception {
        state = SyncItemState.valueOf(me.getAttribute("state"));
        errorMessage = me.getAttribute("errorMessage");
        errorMessageArgs = me.getAttribute("errorMessageArgs");
        
        if ( me.getAttribute("key") != null) {
        	key = new SyncItemKey<String>(me.getAttribute("key"), String.class);
        }

        Item itemErrorMessageDetail = xml.getItem(me, "errorMessageDetail");
        if (itemErrorMessageDetail.isEmpty()) {
        	errorMessageDetail = null;
        } else {
        	errorMessageDetail = itemErrorMessageDetail.getText();
        }        
    }

}
