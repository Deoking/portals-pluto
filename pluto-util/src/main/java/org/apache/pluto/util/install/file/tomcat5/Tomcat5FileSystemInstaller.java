/*
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pluto.util.install.file.tomcat5;

import org.apache.pluto.util.install.InstallationConfig;
import org.apache.pluto.util.install.file.FileSystemInstaller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Tomcat5FileSystemInstaller extends FileSystemInstaller {

//
// PortalInstaller Interface
//

    /**
     * Determine whether or not this is a valid Tomcat 5.x installation.
     * @param installDir the proposed TOMCAT BASE directory
     * @return true if and only if <code>conf/server.xml</code> and
     *         <code>conf/catalina.properties exist.</code>
     */
    public boolean isValidInstallationDirectory(File installDir) {
        File serverConfig = new File(installDir, "conf/server.xml");
        File catalinaProps = new File(installDir, "conf/catalina.properties");
        return serverConfig.exists() && catalinaProps.exists();
    }

//
// FileSystemInstaller Implementation
//

    public void writeConfiguration(InstallationConfig config)
    throws IOException {

        File contextConfigurationDirectory = getConfigurationDir(config);

        Iterator it = config.getPortletApplications().entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String context = entry.getKey().toString();
            File portletApp = (File)entry.getValue();

            String deployed = "../"+config.getServerConfig().getDomain() +
                    "/" +  portletApp.getName();
            String contents = getPortletApplicationConfig(context, deployed);
            FileWriter out = new FileWriter(
                    new File(contextConfigurationDirectory, context+".xml"));
            out.write(contents);
            out.flush();
            out.close();
        }

        File xmlFile =  new File(
                contextConfigurationDirectory,
                config.getPortalContextPath()+".xml"
        );

        FileWriter out = new FileWriter(xmlFile);
        out.write(getPortalApplicationConfig(config));
        out.flush();
        out.close();
    }

    protected File getEndorsedDir(InstallationConfig config ) {
        File installationDirectory = config.getInstallationDirectory();
        return new File(installationDirectory, "common/endorsed");
    }

    protected File getSharedDir(InstallationConfig config) {
        File installationDirectory = config.getInstallationDirectory();
        return new File(installationDirectory, "shared/lib");
    }

    protected File getWebAppDir(InstallationConfig config) {
        File installationDirectory = config.getInstallationDirectory();
        return new File(
            installationDirectory, config.getServerConfig().getDomain()
        );
    }

//
// Helpers
//

    protected File getConfigurationDir(InstallationConfig config) {
        File installationDirectory = config.getInstallationDirectory();
        String engine = "Catalina";
        String host   = config.getServerConfig().getHost();
        return new File(installationDirectory, "conf/"+engine+"/"+host);
    }

    private String getPortalApplicationConfig(InstallationConfig config) {
        String war = "../"+config.getServerConfig().getDomain() +
                "/" +  config.getPortalApplication().getName();
        String contextPath = config.getPortalContextPath();
        return getConfigContents(war, contextPath);
    }

    private String getPortletApplicationConfig(String contextPath, String file) {
        return getConfigContents(file, contextPath);
    }

    private String getConfigContents(String war, String contextPath) {
        StringBuffer contents = new StringBuffer();
        contents.append("<Context ")
                .append("path=\"").append(contextPath).append("\" ")
                .append("docBase=\"").append(war).append("\" ")
                .append("crossContext=\"true\">").append("</Context>");
       return contents.toString();
    }
}