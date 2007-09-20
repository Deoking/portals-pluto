/* 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.pluto.internal.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Locale;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pluto.PortletContainer;
import org.apache.pluto.descriptors.portlet.PortletDD;
import org.apache.pluto.descriptors.portlet.SupportsDD;
import org.apache.pluto.internal.InternalPortletWindow;
import org.apache.pluto.util.ArgumentUtility;
import org.apache.pluto.util.StringManager;
import org.apache.pluto.util.StringUtils;

public class MimeResponseImpl extends PortletResponseImpl implements
		MimeResponse {
	
	/** Logger. */
	private static final Log LOG = LogFactory.getLog(MimeResponseImpl.class);
	
	private static final StringManager EXCEPTIONS = StringManager.getManager(
    		MimeResponseImpl.class.getPackage().getName());
	
	/** The current content type. */
    private String currentContentType = null; 
    
	
	public MimeResponseImpl(PortletContainer container,
            InternalPortletWindow internalPortletWindow,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
		
		super(container, internalPortletWindow, servletRequest,
				servletResponse);
	}
	
	@Override
    public void flushBuffer() throws IOException {
        getHttpServletResponse().flushBuffer();
    }
    
	@Override
    public int getBufferSize() {
        return getHttpServletResponse().getBufferSize();
    }
    
	public CacheControl getCacheControl() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method needs to be implemented.");
	}
	
	@Override
	public String getCharacterEncoding() {
        return getHttpServletResponse().getCharacterEncoding();
    }
	
	@Override
	public String getContentType() {
        return getHttpServletResponse().getContentType();
    }
	
	@Override
	public Locale getLocale() {
        return getHttpServletResponse().getLocale();
    }
	
    /**
     * @see PortletResponseImpl#getOutputStream()
     * @see #getWriter()
     */
	public OutputStream getPortletOutputStream() throws IOException {
		if (currentContentType == null) {
            String message = EXCEPTIONS.getString("error.contenttype.null");
            if (LOG.isWarnEnabled()) {
            	LOG.warn("Current content type is not set.");
            }
            throw new IllegalStateException(message);
        }
        return super.getOutputStream();
	}
	
	/**
     * @see PortletResponseImpl#getWriter()
     * @see #getPortletOutputStream()
     */
	@Override
    public PrintWriter getWriter() throws IOException, IllegalStateException {
        if (currentContentType == null) {
            String message = EXCEPTIONS.getString("error.contenttype.null");
            if (LOG.isWarnEnabled()) {
            	LOG.warn("Current content type is not set.");
            }
            throw new IllegalStateException(message);
        }
        return super.getWriter();
    }
    
	@Override
    public boolean isCommitted() {
        return getHttpServletResponse().isCommitted();
    }
    
	@Override
    public void reset() {
        getHttpServletResponse().reset();
    }
    
	@Override
    public void resetBuffer() {
        getHttpServletResponse().resetBuffer();
    }
    
	@Override
    public void setBufferSize(int size) {
    	getHttpServletResponse().setBufferSize(size);
    }
    
	@Override
    public void setContentType(String contentType)
    		throws IllegalArgumentException {
    	
    	if (super.isIncluded()){
    		//no operation
    	}
    	else{
    		ArgumentUtility.validateNotNull("contentType", contentType);
            String mimeType = StringUtils.getMimeTypeWithoutEncoding(contentType);
            if (!isValidContentType(mimeType)) {
                throw new IllegalArgumentException("Specified content type '"
                		+ mimeType + "' is not supported.");
            }
            getHttpServletResponse().setContentType(mimeType);
            this.currentContentType = mimeType;
    	}
    }
    
    // access to a limited set of HttpServletResponse methods ------------------
	
	@Override
	public void addDateHeader(String arg0, long arg1) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.addProperty(arg0, Long.toString(arg1));
		}
		else
			super.addDateHeader(arg0, arg1);
	}
	
	@Override
	public void addHeader(String arg0, String arg1) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.addProperty(arg0, arg1);
		}
		else
			super.addHeader(arg0, arg1);
	}
	
	@Override
	public void addIntHeader(String arg0, int arg1) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.addProperty(arg0, Integer.toString(arg1));
		}
		else
			super.addIntHeader(arg0, arg1);
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IllegalStateException, IOException {
		if (super.isIncluded() || super.isForwarded()){
			return (ServletOutputStream)getPortletOutputStream();
		}
		else
			return super.getOutputStream();
	}

	@Override
	public void sendRedirect(String arg0) throws IOException {
		if (super.isIncluded() || super.isForwarded()){
			// no operation
		}
		else
			super.sendRedirect(arg0);
	}

	@Override
	public void setDateHeader(String arg0, long arg1) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.setProperty(arg0, Long.toString(arg1));
		}
		else
			super.setDateHeader(arg0, arg1);
	}

	@Override
	public void setHeader(String arg0, String arg1) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.setProperty(arg0, arg1);
		}
		else
			super.setHeader(arg0, arg1);
	}

	@Override
	public void setIntHeader(String arg0, int arg1) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.setProperty(arg0, Integer.toString(arg1));
		}
		else
			super.setIntHeader(arg0, arg1);
	}

	@Override
	public void setStatus(int arg0, String arg1) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.setProperty(arg1, Integer.toString(arg0));
		}
		else
			super.setStatus(arg0, arg1);
	}

	@Override
	public void setStatus(int arg0) {
		if (super.isIncluded()){
			//no operation
		}
		else if (super.isForwarded()){
			super.setProperty("STATUS", Integer.toString(arg0));
		}
		else
		super.setStatus(arg0);
	}
    
    
    // Private Methods ---------------------------------------------------------
    
    /**
     * Checks if the specified content type is valid (supported by the portlet).
     * The specified content type should be a tripped mime type without any
     * character encoding suffix.
     * @param contentType  the content type to check.
     * @return true if the content type is valid, false otherwise.
     */
    private boolean isValidContentType(String contentType) {
    	boolean valid = false;
    	
        PortletDD portletDD = getInternalPortletWindow().getPortletEntity()
        		.getPortletDefinition();
        for (Iterator it = portletDD.getSupports().iterator();
        		!valid && it.hasNext(); ) {
            
        	SupportsDD supportsDD = (SupportsDD) it.next();
            String supportedType = supportsDD.getMimeType();
            
            // Content type is supported by an exact match.
            if (supportedType.equals(contentType)) {
            	valid = true;
            }
            // The supported type contains a wildcard.
            else if (supportedType.indexOf("*") >= 0) {
            	
                int index = supportedType.indexOf("/");
                String supportedPrefix = supportedType.substring(0, index);
                String supportedSuffix = supportedType.substring(index + 1);
                
                index = contentType.indexOf("/");
                String typePrefix = contentType.substring(0, index);
                String typeSuffix = contentType.substring(index + 1);
                
                // Check if the prefixes match AND the suffixes match.
                if (supportedPrefix.equals("*") || supportedPrefix.equals(typePrefix)) {
                    if (supportedSuffix.equals("*") || supportedSuffix.equals(typeSuffix)) {
                        valid = true;
                    }
                }
            }
        }
        // Return the check result.
        return valid;
    }
}