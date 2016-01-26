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

import org.openmrs.module.sync.SyncTransmissionState;
import org.openmrs.module.sync.ingest.SyncTransmissionResponse;
import org.openmrs.module.sync.server.ServerConnectionState;

/**
 *
 */
public class SyncConnectionTestItem {

	private String connectionState;
	private String errorMessage;
	private String responsePayload;
    private String syncTargetUuid;

	public SyncConnectionTestItem() {}

	public SyncConnectionTestItem(SyncTransmissionResponse str) {
		
		if ( str != null ) {
			if ( str.getState().equals(SyncTransmissionState.AUTH_FAILED) ) this.connectionState = ServerConnectionState.AUTHORIZATION_FAILED.toString();
			else if ( str.getState().equals(SyncTransmissionState.OK) ) this.connectionState = ServerConnectionState.OK.toString();
			else this.connectionState = ServerConnectionState.CONNECTION_FAILED.toString();
            
            String targetUuid = str.getSyncTargetUuid();
            if ( targetUuid != null ) {
                this.syncTargetUuid = targetUuid;
            }
		} else {
			this.connectionState = ServerConnectionState.CONNECTION_FAILED.toString();
		}
	}

	public String getConnectionState() {
    	return connectionState;
    }
	
	public void setConnectionState(String connectionState) {
    	this.connectionState = connectionState;
    }
	
	public String getErrorMessage() {
    	return errorMessage;
    }
	
	public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }
	
	public String getResponsePayload() {
    	return responsePayload;
    }
	
	public void setResponsePayload(String responsePayload) {
    	this.responsePayload = responsePayload;
    }

    public String getSyncTargetUuid() {
        return syncTargetUuid;
    }

    public void setSyncTargetUuid(String syncTargetUuid) {
        this.syncTargetUuid = syncTargetUuid;
    }

	
}
