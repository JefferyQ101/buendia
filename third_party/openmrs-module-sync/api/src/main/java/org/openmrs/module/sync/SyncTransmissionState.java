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
package org.openmrs.module.sync;


/**
 * TODO: Comment
 */
public enum SyncTransmissionState {

    /**
     * Transmission is OK - sending/receiving OR sent/received OK
     */
    OK,

    /**
     * Unable to send/receive to the specified server - possible network failure.  OK to try again, as this is likely temporary or configuration problem.
     */
    FAILED,

    /**
     * Transmission was sent/received ok, but some records in it failed.
     */
    FAILED_RECORDS,

    /**
     * Unable to authorize connection to server when trying to sync
     */
    AUTH_FAILED,

    /**
     * Connection to specified server failed.  Most likely invalid address or server is off.
     */
    CONNECTION_FAILED,

    /**
     * Unable to create a sync transmission.  Check error logs
     */
    TRANSMISSION_CREATION_FAILED,

    /**
     * Parent server is undefined while trying to "sync to parent".  Configure first using Admin > Synchronization > Configure
     */
    NO_PARENT_DEFINED,

    /**
     * Response data was corrupted or format that was unexpected.  Check response data/file.
     */
    RESPONSE_NOT_UNDERSTOOD,

    /**
     * Transmission data was not understood by the receiving server.  Check transmission data/file
     */
    TRANSMISSION_NOT_UNDERSTOOD,

    /**
     * URL is invalid.  Check the URL of the server you are trying to connect to.
     */
    MALFORMED_URL,

    /**
     * SSL certificate is invalid.  Check to make sure certificate is valid and/or exists in local keystore.
     */
    CERTIFICATE_FAILED,

    /**
     * No synchronization is needed - server is completely sync'ed with the other server.
     */
    OK_NOTHING_TO_DO, 
    
    /**
     * Server object passed to sync methods was null or non-existent.  This should not ever happen, so troubleshoot code.
     */    
    INVALID_SERVER,
    
    /**
     * Transmission sent/received contains records that have been retried too many times (see global property SyncConstants.PROPERTY_NAME_MAX_RETRY_COUNT)
     */
    MAX_RETRY_REACHED,
    /**
     * Send/receive of data has been initiated however not yet completed with fail or success.
     */
    PENDING,    
    
    /**
     * Cannot run more than one sync task at the same time.
     */
    ERROR_CANNOT_RUN_PARALLEL,
    
    /**
     * Got sync request from child server whose uuid does not match with any of our registered child servers.
     */    
    CANNOT_FIND_SERVER_WITH_UUID;
    
    public boolean isError() {
    	return !SyncConstants.SYNC_TRANSMISSION_OK_STATES.contains(this);
    }
}
