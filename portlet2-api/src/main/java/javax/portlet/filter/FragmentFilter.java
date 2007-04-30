/*  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
/*
 * NOTE: this source code is based on an early draft version of JSR 286 and not intended for product
 * implementations. This file may change or vanish in the final version of the JSR 286 specification.
 */
/*
 * This source code implements specifications defined by the Java
 * Community Process. In order to remain compliant with the specification
 * DO NOT add / change / or delete method signatures!
 */
/*
 * Copyright 2006 IBM Corporation.
 *
 */
package javax.portlet.filter;

import java.io.IOException;

import javax.portlet.FragmentRequest;
import javax.portlet.FragmentResponse;
import javax.portlet.PortletException;

/**
 * The <code>FragmentFilter</code> is an object that performs filtering 
 * tasks on either the fragment request to a portlet, or on the fragment response from 
 * a portlet, or both.
 * <p>
 * Filters perform filtering in the <code>doFilter</code> method. Every Filter has 
 * access to a <code>FilterConfig</code> object from which it can obtain 
 * its initialization parameters, a reference to the PortletContext 
 * which it can use, for example, to load resources needed for filtering tasks.
 * <p>
 * Filters are configured in the portlet deployment descriptor of a 
 * portlet application. 
 * 
 * @since 2.0
 */
public interface FragmentFilter {
    /**
     * Called by the portlet container to indicate to a filter
     * that it is being placed into service. The portlet container 
     * calls the init method exactly once after instantiating the filter. 
     * The init method must complete successfully before the filter 
     * is asked to do any filtering work.
     * <p>
     * The portlet container cannot place the filter into service if the init method either
     * <ul>
     *   <li>throws a PortletException</li>
     *   <li>does not return within a time period defined by the portlet container</li>
     * </ul>
     * 
     * @param filterConfig    the filter configuration data defined 
     *                        in the portlet deployment descriptor
     * @throws PortletException  if an error occurs in the filter intialization
     */
    public void init(FilterConfig filterConfig) throws PortletException;
    
    /**
     * The <code>doFilter</code> method of the Filter is called by the 
     * portlet container each time a fragment request/response pair is passed 
     * through the chain due to a client request for a portlet method 
     * at the end of the chain. 
     * <p>
     * The <code>FilterChain</code> passed in to this method allows 
     * the Filter to pass on the fragment request and response to the next 
     * component in the chain.
     * <p>
     * The <code>doFilter</code> method of a filter will typically be implemented 
     * following this or some subset of the following pattern:
     * <ul>
     *  <li>The method examines the request information.</li>
     *  <li>The method may wrap the request object passed in to 
     *      its doFilter method with a customized implementation 
     *      the request wrapper <code>FragmentRequestWrapper</code> 
     *      in order to modify request data.</li>
     *  <li>The method may wrap the response object passed in to its 
     *      <code>doFilter</code> method with a customized implementation 
     *      of the response wrapper <code>FragmentResponseWrapper</code> 
     *      to modify response data.</li>
     *  <li>The filter may invoke the next comonent in the filter chain. 
     *      The next component may be another filter, or if the filter 
     *      making the invocation is the last filter configured in the 
     *      deployment descriptor for this chain, the next component 
     *      is the target method of the portlet. The invocation of the 
     *      next component is effected by calling the <code>doFilter>/code>
     *      method on the <code>FilterChain</code> object, and passing in 
     *      the request and response with which it was called or passing 
     *      in wrapped versions it may have created. 
     *      The filter chain�s implementation of the <code>doFilter</code> 
     *      method, provided by the portlet container, must locate the 
     *      next component in the filter chain and invoke its <code>doFilter</code>
     *      method, passing in the appropriate request and response objects. 
     *      Alternatively, the filter chain can block the request by not 
     *      making the call to invoke the next component, leaving the filter 
     *      responsible for filling out the response object.</li>
     *  <li>After invocation of the next filter in the chain, the filter 
     *      may examine the response data.</li>
     *  <li>Alternatively, the filter may have thrown an exception to 
     *      indicate an error in processing. If the filter throws an 
     *      <code>UnavailableException</code> during its <code>doFilter</code> 
     *      processing, the portlet container must not attempt continued 
     *      processing down the filter chain. It may choose to retry the 
     *      whole chain at a later time if the exception is not marked permanent.</li>
     *  <li>When the last filter in the chain has been invoked, the next 
     *      component accessed is the target method on the portlet at 
     *      the end of the chain.</li>
     * </ul>
     * 
     * @param request  the current fragment request 
     * @param response  the current fragment response 
     * @param chain  the remaining filter chain
     * @throws IOException  if an IO error occured in the filter processing
     * @throws PortletException  if a portlet exception occured in the filter processing
     */
    public void doFilter(FragmentRequest request, FragmentResponse response,
                         FilterChain chain)
     throws IOException, PortletException;
    
    /**
     * Called by the portlet container to indicate to a filter that it is 
     * being taken out of service. This method is only called once all threads 
     * within the filter's <code>doFilter</code> method have exited or 
     * after a timeout period has passed. 
     * <p>
     * After the portlet container calls this method, it will not call the 
     * <code>doFilter</code> method again on this instance of the filter.
     * <p>
     * This method gives the filter an opportunity to clean up any resources 
     * that are being held (for example, memory, file handles, threads) and 
     * make sure that any persistent state is synchronized with the 
     * filter's current state in memory.
     */
    public void destroy();
    

}