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
package org.openmrs.module.sync.web.dwr;

import org.openmrs.module.sync.SyncRecordState;
import org.openmrs.module.sync.ingest.SyncImportItem;

/**
 *
 */
public class SyncImportItemItem {
    private String key;
    private String state;
    private String errorMessage;
    private String errorMessageArgs;
    
	/**
     * @param item
     */
    public SyncImportItemItem(SyncImportItem item) {
    	if ( item != null ) {
    		if ( item.getKey() != null ) 
    			if ( item.getKey().getKeyValue() != null ) 
    				key = item.getKey().getKeyValue().toString();

    		if ( item.getState() != null ) this.state = item.getState().toString();
    		else this.state = SyncRecordState.FAILED.toString();

    		this.errorMessage = item.getErrorMessage();
    		this.errorMessageArgs = item.getErrorMessageArgs();
    	} else {
    		this.state = SyncRecordState.FAILED.toString();
    	}
    }
	
	public String getErrorMessage() {
    	return errorMessage;
    }
	
	public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }
	
	public String getErrorMessageArgs() {
    	return errorMessageArgs;
    }
	
	public void setErrorMessageArgs(String errorMessageArgs) {
    	this.errorMessageArgs = errorMessageArgs;
    }
	
	public String getKey() {
    	return key;
    }
	
	public void setKey(String key) {
    	this.key = key;
    }
	
	public String getState() {
    	return state;
    }
	
	public void setState(String state) {
    	this.state = state;
    }

    
}
