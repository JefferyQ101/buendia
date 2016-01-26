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
package org.openmrs.module.sync.server;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.sync.SyncException;

/**
 *
 */
public class ConnectionRequest {

	private static final Log log = LogFactory.getLog(ConnectionRequest.class);

	private long checksum;
	private boolean useCompression;
	private ByteArrayOutputStream baos; 
	private CheckedOutputStream cos;             
	private GZIPOutputStream zos;   			
		
	
	/**
	 * Public constructor that creates a request using compression.
	 * 
	 * @param content
	 * @throws SyncException
	 */
	public ConnectionRequest(String content) throws Exception { 
		this(content, true);
	}

	
	/**
	 * Public constructor that creates a quest
	 * 
	 * @param content
	 * @param useCompression
	 * @throws SyncException
	 */
	public ConnectionRequest(String content, boolean useCompression) throws SyncException { 
		try {
			this.useCompression = useCompression;
			this.baos = new ByteArrayOutputStream();
			this.cos = new CheckedOutputStream(baos, new CRC32());			
			
			if (useCompression) { 
				this.zos = new GZIPOutputStream(new BufferedOutputStream(cos));			
				IOUtils.copy(new ByteArrayInputStream(content.getBytes("UTF-8")), zos);
				IOUtils.closeQuietly(zos);		
			} 
			else { 
				IOUtils.copy(new ByteArrayInputStream(content.getBytes("UTF-8")), baos);
				IOUtils.closeQuietly(baos);
			}
			this.checksum = cos.getChecksum().getValue();
						
		} catch (IOException e) { 
			throw new SyncException(e);			
		}		
	}
		
	/**
	 * Get checksum of content.
	 * 
	 * @return
	 */
	public long getChecksum() { 
		return checksum;	
	}
	public long getContentLength() {
		return baos.size();
	}
	
	/**
	 * Returns a compressed or uncompressed data. 
	 * 
	 * @return
	 */
	public byte[] getBytes() { 
		return baos.toByteArray();
	}
	public boolean forceCompression() {
		return useCompression;
	}
	
}
