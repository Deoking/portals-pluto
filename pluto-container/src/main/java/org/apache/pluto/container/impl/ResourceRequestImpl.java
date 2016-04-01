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
package org.apache.pluto.container.impl;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.CacheControl;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceParameters;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;

import org.apache.pluto.container.PortletResourceRequestContext;
import org.apache.pluto.container.PortletResourceResponseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceRequestImpl extends ClientDataRequestImpl implements ResourceRequest
{

   /** Internal logger. */
   private static final Logger LOG = LoggerFactory.getLogger(ResourceRequestImpl.class);

    private CacheControl cacheControl;
    
    public ResourceRequestImpl(PortletResourceRequestContext requestContext, PortletResourceResponseContext responseContext)
    {
        super(requestContext, responseContext, PortletRequest.RESOURCE_PHASE);
        this.cacheControl = responseContext.getCacheControl();
    }
    
    @Override
    protected PortletResourceRequestContext getRequestContext() {
       return (PortletResourceRequestContext) requestContext;
    }
    
    protected PortletResourceResponseContext getResponseContext() {
       return (PortletResourceResponseContext) responseContext;
    }
    
    @Override
    public String getProperty(String name)
    {
        String result = getMimeRequestProperty(name, cacheControl);
        return result != null ? result : super.getProperty(name);
    }

    public String getCacheability()
    {
        return getRequestContext().getCacheability();
    }

    public String getETag()
    {
        return cacheControl.getETag();
    }

    public Map<String, String[]> getPrivateRenderParameterMap()
    {
        return cloneParameterMap(getRequestContext().getPrivateRenderParameterMap());
    }

    public String getResourceID()
	{
		return getRequestContext().getResourceID();
	}

    public String getResponseContentType()
    {
        return getServletRequest().getHeader("accept");
    }

    public Enumeration<String> getResponseContentTypes()
    {
        return getServletRequest().getHeaders("accept");
    }
   
   // Debug code - intercept getParameter call & dump all parameters to trace
   @Override
   public String getParameter(String name) {
      String val = super.getParameter(name);
      if (LOG.isTraceEnabled()) {
         Map<String, String[]> pmap = super.getParameterMap();
         StringBuffer txt = new StringBuffer(1024);
         txt.append("Resource Request parameter map dump:");
         for (String n : pmap.keySet()) {
            txt.append("\nName: " + n + ", Values: ");
            String[] vals = pmap.get(n);
            String sep = "";
            for (String v : vals) {
               txt.append(sep + v);
               sep = ", ";
            }
         }
         LOG.debug(txt.toString());
      }
      return val;
   }

   public ResourceParameters getResourceParameters() {
      return getRequestContext().getResourceParameters();
   }

   @Override
   public AsyncContext startAsync() throws IllegalStateException {
      return getRequestContext().startAsync(this);
   }

   @Override
   public AsyncContext startAsync(ResourceRequest request, ResourceResponse response) throws IllegalStateException {
      return getRequestContext().startAsync(request, response);
   }

   @Override
   public boolean isAsyncStarted() {
      return getRequestContext().isAsyncStarted();
   }

   @Override
   public boolean isAsyncSupported() {
      return getRequestContext().isAsyncSupported();
   }

   @Override
   public AsyncContext getAsyncContext() {
      return getRequestContext().getAsyncContext();
   }

   @Override
   public DispatcherType getDispatcherType() {
      return getRequestContext().getDispatcherType();
   }
}
