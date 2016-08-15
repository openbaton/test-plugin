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

package org.openbaton.plugin.test;

import org.openbaton.catalogue.mano.common.DeploymentFlavour;
import org.openbaton.catalogue.nfvo.NFVImage;
import org.openbaton.catalogue.nfvo.Network;
import org.openbaton.catalogue.nfvo.Quota;
import org.openbaton.catalogue.nfvo.Server;
import org.openbaton.catalogue.nfvo.Subnet;
import org.openbaton.catalogue.nfvo.VimInstance;
import org.openbaton.catalogue.security.Key;
import org.openbaton.plugin.PluginStarter;
import org.openbaton.vim.drivers.interfaces.VimDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * Created by lto on 12/05/15.
 * <p/>
 * This class represents a Vim Driver plugin. As vim driver, it must implement the interface {@Link
 *  ClientInterfaces}. This is just an example that can be used to create a OpenBaton plugin.
 * The basic concept is shared by all the plugins.
 * <p/>
 * The plugin class must be annotated as @Component (at least) and implement the specific interface.
 * There must be a configuration file in the classpath called plugin.conf.properties that contains:
 * <p/>
 * *) sender-type = the type of the sender (default and unique for now: JMS) *) receiver-type = the
 * type of the receiver (default and unique for now: JMS) *) type = the type of the plugin (test,
 * openstack, amazon ...) *) endpoint = the endpoint of the plugin (i.e. queue name) *) concurrency
 * = the concurrency of the receiver (default: 1)
 */
public class TestClient extends VimDriver {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public TestClient() {
    super();
  }

  public static void main(String[] args)
      throws NoSuchMethodException, IOException, InstantiationException, TimeoutException,
          IllegalAccessException, InvocationTargetException {
    if (args.length <= 1)
      PluginStarter.registerPlugin(TestClient.class, "test", "localhost", 5672, 3);
    else
      PluginStarter.registerPlugin(
          TestClient.class,
          args[0],
          args[1],
          Integer.parseInt(args[2]),
          Integer.parseInt(args[3]),
          args[4],
          args[5]);
  }

  @Override
  public Server launchInstance(
      VimInstance vimInstance,
      String name,
      String image,
      String flavor,
      String keypair,
      Set<String> network,
      Set<String> secGroup,
      String userData) {
    return createServer();
  }

  @Override
  public List<NFVImage> listImages(VimInstance vimInstance) {
    ArrayList<NFVImage> nfvImages = new ArrayList<>();
    NFVImage image = new NFVImage();
    image.setExtId("ext_id");
    image.setName("ubuntu-14.04-server-cloudimg-amd64-disk1");
    nfvImages.add(image);

    for (int i = 1; i <= 20; i++) {
      NFVImage img = new NFVImage();
      img.setExtId("ext_id_" + i);
      img.setName("image_name_" + i);
      nfvImages.add(img);
    }

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
    networks.add(createNetwork());
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
  public Server launchInstanceAndWait(
      VimInstance vimInstance,
      String hostname,
      String image,
      String extId,
      String keyPair,
      Set<String> networks,
      Set<String> securityGroups,
      String s) {
    return launchInstanceAndWait(
        vimInstance, hostname, image, extId, keyPair, networks, securityGroups, s, null, null);
  }

  @Override
  public Server launchInstanceAndWait(
      VimInstance vimInstance,
      String hostname,
      String image,
      String extId,
      String keyPair,
      Set<String> networks,
      Set<String> securityGroups,
      String s,
      Map<String, String> floatingIps,
      Set<Key> keys) {
    try {
      Thread.sleep((long) (Math.random() * 3500));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Server server = createServer();
    log.debug("Created server: " + server);
    return server;
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
  public NFVImage addImage(VimInstance vimInstance, NFVImage image, byte[] imageFile) {
    return image;
  }

  @Override
  public NFVImage addImage(VimInstance vimInstance, NFVImage image, String image_url) {
    return image;
  }

  @Override
  public NFVImage updateImage(VimInstance vimInstance, NFVImage image) {
    return image;
  }

  @Override
  public NFVImage copyImage(VimInstance vimInstance, NFVImage image, byte[] imageFile) {
    return image;
  }

  @Override
  public boolean deleteImage(VimInstance vimInstance, NFVImage image) {
    return true;
  }

  @Override
  public DeploymentFlavour updateFlavor(
      VimInstance vimInstance, DeploymentFlavour deploymentFlavour) {
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
    return new ArrayList<String>();
  }

  @Override
  public boolean deleteSubnet(VimInstance vimInstance, String existingSubnetExtId) {
    return true;
  }

  @Override
  public boolean deleteNetwork(VimInstance vimInstance, String extId) {
    return true;
  }

  @Override
  public Network getNetworkById(VimInstance vimInstance, String id) {
    return createNetwork();
  }

  @Override
  public Quota getQuota(VimInstance vimInstance) {
    return createQuota();
  }

  @Override
  public String getType(VimInstance vimInstance) {
    return "test";
  }

  private Server createServer() {
    Server server = new Server();
    server.setName("server_name");
    server.setExtId("ext_id");
    server.setCreated(new Date());
    server.setFloatingIps(new HashMap<String, String>());
    server.setExtendedStatus("ACTIVE");
    DeploymentFlavour flavor = new DeploymentFlavour();
    flavor.setDisk(100);
    flavor.setExtId("ext");
    flavor.setFlavour_key("m1.small");
    flavor.setRam(2000);
    flavor.setVcpus(4);
    server.setFlavor(flavor);
    server.setIps(new HashMap<String, List<String>>());
    return server;
  }

  private Network createNetwork() {
    Network network = new Network();
    network.setName("network_name");
    network.setId("network-id");
    network.setExtId("ext_id");
    return network;
  }

  private Quota createQuota() {
    Quota quota = new Quota();
    quota.setCores(99999);
    quota.setFloatingIps(99999);
    quota.setInstances(99999);
    quota.setKeyPairs(999999);
    quota.setRam(99999999);
    quota.setTenant("test-tenant");
    return quota;
  }
}
