/*
 * Copyright (c) 2016 Open Baton (http://www.openbaton.org)
 *
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import org.openbaton.catalogue.mano.common.DeploymentFlavour;
import org.openbaton.catalogue.mano.descriptor.VNFDConnectionPoint;
import org.openbaton.catalogue.nfvo.Quota;
import org.openbaton.catalogue.nfvo.Server;
import org.openbaton.catalogue.nfvo.images.BaseNfvImage;
import org.openbaton.catalogue.nfvo.networks.BaseNetwork;
import org.openbaton.catalogue.nfvo.networks.Subnet;
import org.openbaton.catalogue.nfvo.viminstances.BaseVimInstance;
import org.openbaton.catalogue.nfvo.viminstances.GenericVimInstance;
import org.openbaton.catalogue.security.Key;
import org.openbaton.exceptions.VimDriverException;
import org.openbaton.plugin.PluginStarter;
import org.openbaton.vim.drivers.interfaces.VimDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a Vim Driver plugin. As vim driver, it must implement the interface {@Link
 * ClientInterfaces}. This is just an example that can be used to create a OpenBaton plugin. The
 * basic concept is shared by all the plugins.
 *
 * <p>The plugin class must be annotated as @Component (at least) and implement the specific
 * interface. There must be a configuration file in the classpath called plugin.conf.properties that
 * contains:
 *
 * <p>*) sender-type = the type of the sender (default and unique for now: JMS) *) receiver-type =
 * the type of the receiver (default and unique for now: JMS) *) type = the type of the plugin
 * (test, openstack, amazon ...) *) endpoint = the endpoint of the plugin (i.e. queue name) *)
 * concurrency = the concurrency of the receiver (default: 1)
 */
public class TestClient extends VimDriver {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public TestClient() {
    super();
  }

  public static void main(String[] args)
      throws NoSuchMethodException, IOException, InstantiationException, TimeoutException,
          IllegalAccessException, InvocationTargetException, InterruptedException {
    if (args.length <= 1)
      PluginStarter.registerPlugin(TestClient.class, "test", "localhost", 5672, 3);
    else
      PluginStarter.registerPlugin(
          TestClient.class, args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
  }

  @Override
  public Server launchInstance(
      BaseVimInstance vimInstance,
      String name,
      String image,
      String flavor,
      String keypair,
      Set<VNFDConnectionPoint> network,
      Set<String> secGroup,
      String userData) {
    return createServer();
  }

  @Override
  public List<BaseNfvImage> listImages(BaseVimInstance vimInstance) {
    ArrayList<BaseNfvImage> nfvImages = new ArrayList<>();
    BaseNfvImage image = new BaseNfvImage();
    image.setExtId("ubuntu-14.04-server-cloudimg-amd64-disk1");
    //    image.setName("ubuntu-14.04-server-cloudimg-amd64-disk1");
    //    image.setContainerFormat("BARE");
    //    image.setDiskFormat("QCOW2");
    //    image.setMinRam(0);
    //    image.setMinCPU("1");
    //    image.setMinDiskSpace(0);
    //    image.setIsPublic(Math.random() >= 0.5);
    image.setCreated(new Date());
    //    image.setUpdated(new Date());
    nfvImages.add(image);

    image = new BaseNfvImage();
    image.setExtId("ubuntu-16.04-server-cloudimg-amd64-disk1");
    //    image.setName("ubuntu-16.04-server-cloudimg-amd64-disk1");
    //    image.setContainerFormat("BARE");
    //    image.setDiskFormat("QCOW2");
    //    image.setMinRam(0);
    //    image.setMinCPU("1");
    //    image.setMinDiskSpace(0);
    //    image.setIsPublic(Math.random() >= 0.5);
    image.setCreated(new Date());
    //    image.setUpdated(new Date());
    nfvImages.add(image);

    image = new BaseNfvImage();
    image.setExtId("ubuntu-14.04");
    //    image.setName("ubuntu-14.04");
    //    image.setContainerFormat("BARE");
    //    image.setDiskFormat("QCOW2");
    //    image.setMinRam(0);
    //    image.setMinCPU("1");
    //    image.setMinDiskSpace(0);
    //    image.setIsPublic(Math.random() >= 0.5);
    image.setCreated(new Date());
    //    image.setUpdated(new Date());
    nfvImages.add(image);

    image = new BaseNfvImage();
    image.setExtId("ubuntu-16.04");
    //    image.setName("ubuntu-16.04");
    //    image.setContainerFormat("BARE");
    //    image.setDiskFormat("QCOW2");
    //    image.setMinRam(0);
    //    image.setMinCPU("1");
    //    image.setMinDiskSpace(0);
    //    image.setIsPublic(Math.random() >= 0.5);
    image.setCreated(new Date());
    //    image.setUpdated(new Date());
    nfvImages.add(image);

    image = new BaseNfvImage();
    image.setExtId("Ubuntu-16.04");
    //    image.setName("ubuntu-16.04");
    //    image.setContainerFormat("BARE");
    //    image.setDiskFormat("QCOW2");
    //    image.setMinRam(0);
    //    image.setMinCPU("1");
    //    image.setMinDiskSpace(0);
    //    image.setIsPublic(Math.random() >= 0.5);
    image.setCreated(new Date());
    //    image.setUpdated(new Date());
    nfvImages.add(image);

    for (int i = 1; i <= 20; i++) {
      BaseNfvImage img = new BaseNfvImage();
      img.setExtId("ext_id_" + i);
      //      img.setName("image_name_" + i);
      //      img.setContainerFormat("BARE");
      //      img.setDiskFormat("QCOW2");
      //      img.setMinRam(0);
      //      img.setMinCPU("1");
      //      img.setMinDiskSpace(0);
      //      img.setIsPublic(Math.random() >= 0.5);
      img.setCreated(new Date());
      //      img.setUpdated(new Date());
      nfvImages.add(img);
    }

    return nfvImages;
  }

  @Override
  public List<Server> listServer(BaseVimInstance vimInstance) {
    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server();
    server.setName("server_name");
    server.setExtId("ext_id");
    DeploymentFlavour flavor = new DeploymentFlavour();
    flavor.setRam(10);
    flavor.setVcpus(1);
    server.setFlavor(flavor);
    server.setIps(new HashMap<>());
    servers.add(server);
    return servers;
  }

  @Override
  public Server rebuildServer(BaseVimInstance vimInstance, String serverId, String imageId)
      throws VimDriverException {
    return createServer();
  }

  @Override
  public List<BaseNetwork> listNetworks(BaseVimInstance vimInstance) {
    ArrayList<BaseNetwork> networks = new ArrayList<>();
    for (int i = 0; i < 20; i++) networks.add(createNetwork("net_" + i, "id_" + i));

    networks.add(createNetwork("mgmt", "id_mgmt"));
    networks.add(createNetwork("net_a", "id_nat_a"));
    networks.add(createNetwork("net_b", "id_nat_b"));
    networks.add(createNetwork("net_c", "id_nat_c"));
    networks.add(createNetwork("net_d", "id_nat_d"));
    return networks;
  }

  @Override
  public List<DeploymentFlavour> listFlavors(BaseVimInstance vimInstance) {
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
  public BaseVimInstance refresh(BaseVimInstance vimInstance) throws VimDriverException {
    log.info(String.format("Executing refresh on vim: %s", vimInstance.getName()));
    GenericVimInstance genericVimInstance = (GenericVimInstance) vimInstance;

    List<BaseNfvImage> newImages = listImages(vimInstance);
    if (genericVimInstance.getImages() == null) {
      genericVimInstance.setImages(new HashSet<>());
    }
    genericVimInstance.getImages().clear();
    genericVimInstance.addAllImages(newImages);

    List<BaseNetwork> newNetworks = listNetworks(vimInstance);

    if (genericVimInstance.getNetworks() == null) {
      genericVimInstance.setNetworks(new HashSet<>());
    }
    genericVimInstance.getNetworks().clear();
    genericVimInstance.addAllNetworks(newNetworks);

    return genericVimInstance;
  }

  @Override
  public Server launchInstanceAndWait(
      BaseVimInstance vimInstance,
      String hostname,
      String image,
      String extId,
      String keyPair,
      Set<VNFDConnectionPoint> networks,
      Set<String> securityGroups,
      String s) {
    return launchInstanceAndWait(
        vimInstance, hostname, image, extId, keyPair, networks, securityGroups, s, null, null);
  }

  @Override
  public Server launchInstanceAndWait(
      BaseVimInstance vimInstance,
      String hostname,
      String image,
      String extId,
      String keyPair,
      Set<VNFDConnectionPoint> networks,
      Set<String> securityGroups,
      String s,
      Map<String, String> floatingIps,
      Set<Key> keys) {
    log.info(
        String.format(
            "Executing launch instance for %s on vim: %s", hostname, vimInstance.getName()));
    try {
      Thread.sleep(
          (int)
                  (Math.random()
                      * Long.parseLong(
                          properties.getProperty("launch-instance-wait-random", "10000")))
              + Long.parseLong(properties.getProperty("launch-instance-wait", "10000")));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Server server = createServer();
    log.debug("Created server: " + server);
    return server;
  }

  @Override
  public void deleteServerByIdAndWait(BaseVimInstance vimInstance, String id) {
    log.info(String.format("Executing delete of %s on vim: %s", id, vimInstance.getName()));
    try {
      Thread.sleep((long) (Math.random() * 1500));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public BaseNetwork createNetwork(BaseVimInstance vimInstance, BaseNetwork network) {
    log.info(String.format("Executing create network on vim: %s", vimInstance.getName()));
    return network;
  }

  @Override
  public DeploymentFlavour addFlavor(
      BaseVimInstance vimInstance, DeploymentFlavour deploymentFlavour) {
    return deploymentFlavour;
  }

  @Override
  public BaseNfvImage addImage(BaseVimInstance vimInstance, BaseNfvImage image, byte[] imageFile) {
    return image;
  }

  @Override
  public BaseNfvImage addImage(BaseVimInstance vimInstance, BaseNfvImage image, String image_url) {
    return (BaseNfvImage) image;
  }

  @Override
  public BaseNfvImage updateImage(BaseVimInstance vimInstance, BaseNfvImage image) {
    return (BaseNfvImage) image;
  }

  @Override
  public BaseNfvImage copyImage(BaseVimInstance vimInstance, BaseNfvImage image, byte[] imageFile) {
    return (BaseNfvImage) image;
  }

  @Override
  public boolean deleteImage(BaseVimInstance vimInstance, BaseNfvImage image) {
    return true;
  }

  @Override
  public DeploymentFlavour updateFlavor(
      BaseVimInstance vimInstance, DeploymentFlavour deploymentFlavour) {
    return deploymentFlavour;
  }

  @Override
  public boolean deleteFlavor(BaseVimInstance vimInstance, String extId) {
    return true;
  }

  @Override
  public Subnet createSubnet(
      BaseVimInstance vimInstance, BaseNetwork createdNetwork, Subnet subnet) {
    log.info(String.format("Executing create subnet on vim: %s", vimInstance.getName()));
    return subnet;
  }

  @Override
  public BaseNetwork updateNetwork(BaseVimInstance vimInstance, BaseNetwork network) {
    return (BaseNetwork) network;
  }

  @Override
  public Subnet updateSubnet(
      BaseVimInstance vimInstance, BaseNetwork updatedNetwork, Subnet subnet) {
    return subnet;
  }

  @Override
  public List<String> getSubnetsExtIds(BaseVimInstance vimInstance, String network_extId) {
    return new ArrayList<String>();
  }

  @Override
  public boolean deleteSubnet(BaseVimInstance vimInstance, String existingSubnetExtId) {
    return true;
  }

  @Override
  public boolean deleteNetwork(BaseVimInstance vimInstance, String extId) {
    return true;
  }

  @Override
  public BaseNetwork getNetworkById(BaseVimInstance vimInstance, String id) {
    return createNetwork("net_name", id);
  }

  @Override
  public Quota getQuota(BaseVimInstance vimInstance) {
    return createQuota();
  }

  @Override
  public String getType(BaseVimInstance vimInstance) {
    return "test";
  }

  private Server createServer() {
    Server server = new Server();
    server.setName("server_name");
    server.setExtId("ext_id");
    server.setCreated(new Date());
    server.setFloatingIps(new HashMap<>());
    server.setExtendedStatus("ACTIVE");
    DeploymentFlavour flavor = new DeploymentFlavour();
    flavor.setDisk(100);
    flavor.setExtId("ext");
    flavor.setFlavour_key("m1.small");
    flavor.setRam(2000);
    flavor.setVcpus(4);
    server.setFlavor(flavor);
    server.setIps(new HashMap<>());
    return server;
  }

  private BaseNetwork createNetwork(String networkName, String networkId) {
    BaseNetwork network = new BaseNetwork();
    network.setName(networkName);
    network.setExtId(networkId);
    Subnet subnet = new Subnet();
    subnet.setName(network.getName() + "_subnet");
    subnet.setCidr("192.168.1." + (int) (Math.random() * 100) + "/24");
    subnet.setExtId("subnet_" + networkId);
    subnet.setGatewayIp("192.168.1.1");
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
