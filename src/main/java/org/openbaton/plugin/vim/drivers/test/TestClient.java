/*
 * Copyright (c) 2015. Fraunhofer FOKUS
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openbaton.plugin.vim.drivers.test;

import org.openbaton.catalogue.mano.common.DeploymentFlavour;
import org.openbaton.catalogue.nfvo.*;
import org.openbaton.vim.drivers.exceptions.VimDriverException;
import org.openbaton.vim.drivers.interfaces.ClientInterfaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by lto on 12/05/15.
 *
 * This class represents a Vim Driver plugin. As vim driver, it must implement the interface {@Link ClientInterfaces}.
 * This is just an example that can be used to create a OpenBaton plugin. The basic concept is shared by all the plugins.
 *
 * The plugin class must be annotated as @Component (at least) and implement the specific interface.
 * There must be a configuration file in the classpath called plugin.conf.properties that contains:
 *
 *  *) sender-type = the type of the sender (default and unique for now: JMS)
 *  *) receiver-type = the type of the receiver (default and unique for now: JMS)
 *  *) type = the type of the plugin (test, openstack, amazon ...)
 *  *) endpoint = the endpoint of the plugin (i.e. queue name)
 *  *) concurrency = the concurrency of the receiver (default: 1)
 *
 */
public class TestClient implements ClientInterfaces {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Server launchInstance(VimInstance vimInstance, String name, String image, String flavor, String keypair, Set<String> network, Set<String> secGroup, String userData) {
        throw new UnsupportedOperationException();
    }

//    @Override
//    public void init(VimInstance vimInstance) {
//        log.debug("Initilizing testClient");
//    }

    @Override
    public List<NFVImage> listImages(VimInstance vimInstance) {
        ArrayList<NFVImage> nfvImages = new ArrayList<>();
        NFVImage image = new NFVImage();
        image.setExtId("ext_id_1");
        image.setName("ubuntu-14.04-server-cloudimg-amd64-disk1");
        nfvImages.add(image);

        image = new NFVImage();
        image.setExtId("ext_id_2");
        image.setName("image_name_1");
        nfvImages.add(image);
        return nfvImages;
    }

    @Override
    public List<Server> listServer(VimInstance vimInstance) {
        ArrayList<Server> servers = new ArrayList<>();
        Server server = new Server();
        server.setName("server_name");
        server.setExtId("ext_id");
        DeploymentFlavour flavor = new DeploymentFlavour();
        flavor.setRam(10);
        flavor.setVcpus(1);
        server.setFlavor(flavor);
        server.setIps(new HashMap<String, List<String>>());
        servers.add(server);
        return servers;

    }

    @Override
    public List<Network> listNetworks(VimInstance vimInstance) {
        ArrayList<Network> networks = new ArrayList<>();
        Network network = new Network();
        network.setExtId("ext_id");
        network.setName("network_name");
        networks.add(network);
        return networks;
    }

    @Override
    public List<DeploymentFlavour> listFlavors(VimInstance vimInstance) {
        ArrayList<DeploymentFlavour> deploymentFlavours = new ArrayList<>();
        DeploymentFlavour deploymentFlavour = new DeploymentFlavour();
        deploymentFlavour.setExtId("ext_id_1");
        deploymentFlavour.setFlavour_key("flavor_name");
        deploymentFlavours.add(deploymentFlavour);

        deploymentFlavour = new DeploymentFlavour();
        deploymentFlavour.setExtId("ext_id_6");
        deploymentFlavour.setFlavour_key("m1.nano");
        deploymentFlavours.add(deploymentFlavour);

        deploymentFlavour = new DeploymentFlavour();
        deploymentFlavour.setExtId("ext_id_2");
        deploymentFlavour.setFlavour_key("m1.tiny");
        deploymentFlavours.add(deploymentFlavour);

        deploymentFlavour = new DeploymentFlavour();
        deploymentFlavour.setExtId("ext_id_3");
        deploymentFlavour.setFlavour_key("m1.small");

        deploymentFlavours.add(deploymentFlavour);
        return deploymentFlavours;
    }

    @Override
    public Server launchInstanceAndWait(VimInstance vimInstance, String hostname, String image, String extId, String keyPair, Set<String> networks, Set<String> securityGroups, String s) {
        try {
            Thread.sleep((long) (Math.random() * 3500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Server server = new Server();
        server.setName("server_name");
        server.setExtId("ext_id");
        server.setIps(new HashMap<String , List<String>>());
        return server;
    }

    @Override
    public Server launchInstanceAndWait(VimInstance vimInstance, String hostname, String image, String extId, String keyPair, Set<String> networks, Set<String> securityGroups, String s, boolean floatingIp) throws VimDriverException, RemoteException {
        return launchInstanceAndWait(vimInstance, hostname, image, extId, keyPair, networks, securityGroups, s, false);
    }

    @Override
    public void deleteServerByIdAndWait(VimInstance vimInstance, String id) {
        try {
            Thread.sleep((long) (Math.random() * 1500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Network createNetwork(VimInstance vimInstance, Network network) {
        return network;
    }

    @Override
    public DeploymentFlavour addFlavor(VimInstance vimInstance, DeploymentFlavour deploymentFlavour) {
        return deploymentFlavour;
    }

    @Override
    public NFVImage addImage(VimInstance vimInstance, NFVImage image, byte[] imageFile) throws RemoteException {
        return image;
    }

    @Override
    public NFVImage updateImage(VimInstance vimInstance, NFVImage image) {
        return image;
    }

    @Override
    public NFVImage copyImage(VimInstance vimInstance, NFVImage image, byte[] imageFile) throws RemoteException {
        return image;
    }

    @Override
    public boolean deleteImage(VimInstance vimInstance, NFVImage image) {
        return true;
    }

    @Override
    public DeploymentFlavour updateFlavor(VimInstance vimInstance, DeploymentFlavour deploymentFlavour) {
        return deploymentFlavour;
    }

    @Override
    public boolean deleteFlavor(VimInstance vimInstance, String extId) {
        return true;
    }

    @Override
    public Subnet createSubnet(VimInstance vimInstance, Network createdNetwork, Subnet subnet) {
        return subnet;
    }

    @Override
    public Network updateNetwork(VimInstance vimInstance, Network network) {
        return network;
    }

    @Override
    public Subnet updateSubnet(VimInstance vimInstance, Network updatedNetwork, Subnet subnet) {
        return subnet;
    }

    @Override
    public List<String> getSubnetsExtIds(VimInstance vimInstance, String network_extId) {
        return null;
    }

    @Override
    public boolean deleteSubnet(VimInstance vimInstance, String existingSubnetExtId) {
        return false;
    }

    @Override
    public boolean deleteNetwork(VimInstance vimInstance, String extId) {
        return false;
    }

    @Override
    public Network getNetworkById(VimInstance vimInstance, String id) {
        return null;
    }

    @Override
    public Quota getQuota(VimInstance vimInstance) {
        Quota quota = new Quota();
        quota.setCores(99999);
        quota.setFloatingIps(99999);
        quota.setInstances(99999);
        quota.setKeyPairs(999999);
        quota.setRam(99999999);
        quota.setTenant("test-tenant");
        return quota;
    }

    @Override
    public String getType(VimInstance vimInstance) {
        return "test";
    }
}
