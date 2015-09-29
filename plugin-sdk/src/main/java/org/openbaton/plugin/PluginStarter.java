/*
 * Copyright (c) 2015 Fraunhofer FOKUS
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

package org.openbaton.plugin;

import org.openbaton.vim.drivers.interfaces.ClientInterfaces;
import org.openbaton.plugin.utils.StartupPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 * Created by lto on 09/09/15.
 */
public class PluginStarter {

    protected static Logger log = LoggerFactory.getLogger(PluginStarter.class);

    public static void run(Class clazz, final String name, final String registryIp) {
        try {
            log.info("Starting plugin with name: " + name);
            log.debug("Registry ip: " + registryIp);
            log.debug("Class to register: " + clazz.getName());
            Properties properties = new Properties();
            properties.load(clazz.getResourceAsStream("/plugin.conf.properties"));
            String inte = "";
            for (Class interf : clazz.getInterfaces())
                if (interf.getName().equals(ClientInterfaces.class.getName()))
                    inte = "vim-drivers";
                else
                    inte = "monitor";
            StartupPlugin.register(clazz, inte + "." + properties.getProperty("type", "unknown") + "." + name, registryIp, 1099);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    mainThread.join();
                    log.info("Unregistering: " + name);
                    StartupPlugin.unregister(name, registryIp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void run(Class clazz, final String name, final String registryIp, int port) {
        try {
            log.info("Starting plugin with name: " + name);
            log.debug("Registry ip: " + registryIp);
            log.debug("Class to register: " + clazz.getName());
            Properties properties = new Properties();
            properties.load(clazz.getResourceAsStream("/plugin.conf.properties"));
            String inte = "";
            for (Class interf : clazz.getInterfaces())
                if (interf.getName().equals(ClientInterfaces.class.getName()))
                    inte = "vim-drivers";
                else
                    inte = "monitor";


            StartupPlugin.register(clazz, inte + "." + properties.getProperty("type", "unknown") + "." + name, registryIp, port);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    mainThread.join();
                    log.info("Unregistering: " + name);
                    StartupPlugin.unregister(name, registryIp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
