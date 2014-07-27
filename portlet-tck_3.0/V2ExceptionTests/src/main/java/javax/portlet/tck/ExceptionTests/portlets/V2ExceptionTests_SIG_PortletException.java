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

package javax.portlet.tck.ExceptionTests.portlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.*;
import javax.portlet.filter.*;
import javax.portlet.tck.beans.ClassChecker;
import javax.portlet.tck.beans.TestCaseDetails;
import javax.portlet.tck.beans.JSR286ApiTestCaseDetails;
import static javax.portlet.tck.beans.JSR286ApiTestCaseDetails.*;
import javax.portlet.tck.beans.TestResult;

/**
 * This portlet implements several test cases for the JSR 362 TCK. The test case names
 * are defined in the /src/main/resources/xml-resources/additionalTCs.xml
 * file. The build process will integrate the test case names defined in the 
 * additionalTCs.xml file into the complete list of test case names for execution by the driver.
 */
public class V2ExceptionTests_SIG_PortletException implements Portlet {
   private static final String LOG_CLASS = 
         V2ExceptionTests_SIG_PortletException.class.getName();
   private final Logger LOGGER = Logger.getLogger(LOG_CLASS);
   
   private PortletConfig portletConfig = null;

   @Override
   public void init(PortletConfig config) throws PortletException {
      this.portletConfig = config;
   }

   @Override
   public void destroy() {
   }

   @Override
   public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
         throws PortletException, IOException {
   }

   @Override
   public void render(RenderRequest renderRequest, RenderResponse renderResponse)
         throws PortletException, IOException {
      
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.logp(Level.FINE, LOG_CLASS, "render", "Entry");
      }

      PrintWriter writer = renderResponse.getWriter();
      JSR286ApiTestCaseDetails tcd = new JSR286ApiTestCaseDetails();
      ClassChecker cc = new ClassChecker(PortletException.class);

      // Create result objects for the tests

      /* TestCase: PortletException_SIG_extendsException */
      /* Details: "Extends Exception " */
      TestResult tr0 = tcd.getTestResultFailed(PORTLETEXCEPTION_SIG_EXTENDSEXCEPTION);
      {
         tr0.setTcSuccess(cc.hasSuperclass(Exception.class));
      }

      /* TestCase: PortletException_SIG_constructor */
      /* Details: "Provides constructor PortletException() " */
      TestResult tr1 = tcd.getTestResultFailed(PORTLETEXCEPTION_SIG_CONSTRUCTOR);
      {
         Class<?>[] parms = null;
         tr1.setTcSuccess(cc.hasConstructor(parms));
      }

      /* TestCase: PortletException_SIG_constructorA */
      /* Details: "Provides constructor PortletException(String) " */
      TestResult tr2 = tcd.getTestResultFailed(PORTLETEXCEPTION_SIG_CONSTRUCTORA);
      {
         Class<?>[] parms = {String.class};
         tr2.setTcSuccess(cc.hasConstructor(parms));
      }

      /* TestCase: PortletException_SIG_constructorB */
      /* Details: "Provides constructor PortletException(String, Throwable) " */
      TestResult tr3 = tcd.getTestResultFailed(PORTLETEXCEPTION_SIG_CONSTRUCTORB);
      {
         Class<?>[] parms = {String.class, Throwable.class};
         tr3.setTcSuccess(cc.hasConstructor(parms));
      }

      /* TestCase: PortletException_SIG_constructorC */
      /* Details: "Provides constructor PortletException(Throwable) " */
      TestResult tr4 = tcd.getTestResultFailed(PORTLETEXCEPTION_SIG_CONSTRUCTORC);
      {
         Class<?>[] parms = {Throwable.class};
         tr4.setTcSuccess(cc.hasConstructor(parms));
      }



      // Write the results to the output stream

      tr0.writeTo(writer);
      tr1.writeTo(writer);
      tr2.writeTo(writer);
      tr3.writeTo(writer);
      tr4.writeTo(writer);


   }

}

