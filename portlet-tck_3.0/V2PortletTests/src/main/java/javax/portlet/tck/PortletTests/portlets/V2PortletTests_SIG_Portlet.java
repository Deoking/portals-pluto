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

package javax.portlet.tck.PortletTests.portlets;

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
public class V2PortletTests_SIG_Portlet implements Portlet {
   private static final String LOG_CLASS = 
         V2PortletTests_SIG_Portlet.class.getName();
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
      ClassChecker cc = new ClassChecker(Portlet.class);

      // Create result objects for the tests

      /* TestCase: Portlet_SIG_hasInit */
      /* Details: "Has a init(PortletConfig) throws PortletException method " */
      TestResult tr0 = tcd.getTestResultFailed(PORTLET_SIG_HASINIT);
      {
         String name = "init";
         Class<?>[] exceptions = {PortletException.class};
         Class<?>[] parms = {PortletConfig.class};
         tr0.setTcSuccess(cc.hasMethod(name, parms, exceptions));
      }

      /* TestCase: Portlet_SIG_hasInitReturns */
      /* Details: "Method init(PortletConfig) returns void " */
      TestResult tr1 = tcd.getTestResultFailed(PORTLET_SIG_HASINITRETURNS);
      {
         String name = "init";
         Class<?> retType = void.class;
         Class<?>[] parms = {PortletConfig.class};
         tr1.setTcSuccess(cc.methodHasReturnType(name, retType, parms));
      }

      /* TestCase: Portlet_SIG_hasProcessAction */
      /* Details: "Has a processAction(ActionRequest, ActionResponse) throws PortletException, java.io.IOException method " */
      TestResult tr2 = tcd.getTestResultFailed(PORTLET_SIG_HASPROCESSACTION);
      {
         String name = "processAction";
         Class<?>[] exceptions = {PortletException.class, java.io.IOException.class};
         Class<?>[] parms = {ActionRequest.class, ActionResponse.class};
         tr2.setTcSuccess(cc.hasMethod(name, parms, exceptions));
      }

      /* TestCase: Portlet_SIG_hasProcessActionReturns */
      /* Details: "Method processAction(ActionRequest, ActionResponse) returns void " */
      TestResult tr3 = tcd.getTestResultFailed(PORTLET_SIG_HASPROCESSACTIONRETURNS);
      {
         String name = "processAction";
         Class<?> retType = void.class;
         Class<?>[] parms = {ActionRequest.class, ActionResponse.class};
         tr3.setTcSuccess(cc.methodHasReturnType(name, retType, parms));
      }

      /* TestCase: Portlet_SIG_hasRender */
      /* Details: "Has a render(RenderRequest, RenderResponse) throws PortletException, java.io.IOException method " */
      TestResult tr4 = tcd.getTestResultFailed(PORTLET_SIG_HASRENDER);
      {
         String name = "render";
         Class<?>[] exceptions = {PortletException.class, java.io.IOException.class};
         Class<?>[] parms = {RenderRequest.class, RenderResponse.class};
         tr4.setTcSuccess(cc.hasMethod(name, parms, exceptions));
      }

      /* TestCase: Portlet_SIG_hasRenderReturns */
      /* Details: "Method render(RenderRequest, RenderResponse) returns void " */
      TestResult tr5 = tcd.getTestResultFailed(PORTLET_SIG_HASRENDERRETURNS);
      {
         String name = "render";
         Class<?> retType = void.class;
         Class<?>[] parms = {RenderRequest.class, RenderResponse.class};
         tr5.setTcSuccess(cc.methodHasReturnType(name, retType, parms));
      }

      /* TestCase: Portlet_SIG_hasDestroy */
      /* Details: "Has a destroy()  method " */
      TestResult tr6 = tcd.getTestResultFailed(PORTLET_SIG_HASDESTROY);
      {
         String name = "destroy";
         Class<?>[] exceptions = null;
         Class<?>[] parms = null;
         tr6.setTcSuccess(cc.hasMethod(name, parms, exceptions));
      }

      /* TestCase: Portlet_SIG_hasDestroyReturns */
      /* Details: "Method destroy() returns void " */
      TestResult tr7 = tcd.getTestResultFailed(PORTLET_SIG_HASDESTROYRETURNS);
      {
         String name = "destroy";
         Class<?> retType = void.class;
         Class<?>[] parms = null;
         tr7.setTcSuccess(cc.methodHasReturnType(name, retType, parms));
      }



      // Write the results to the output stream

      tr0.writeTo(writer);
      tr1.writeTo(writer);
      tr2.writeTo(writer);
      tr3.writeTo(writer);
      tr4.writeTo(writer);
      tr5.writeTo(writer);
      tr6.writeTo(writer);
      tr7.writeTo(writer);


   }

}

