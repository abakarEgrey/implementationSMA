package com.irit.upnp;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.*;
import org.fourthline.cling.binding.annotations.*;
import org.fourthline.cling.model.*;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.*;
import java.io.IOException;

/**
 * Classe qui permet de lancer le serveur de lampe binaire UPnP
 * @author tbille
 *
 */

public class BinaryLightServer implements Runnable {
	/**
	 * Main
	 * Copy code if you need to add a Upnp service on your device
	 * @param args
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception {
        // Start a user thread that runs the UPnP stack
        Thread serverThread = new Thread(new BinaryLightServer());
        serverThread.setDaemon(false);
        serverThread.start();
    }
    
    /**
     * Run the UPnP service
     */
    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createDevice()
            );

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    /**
     * Permet de créer un device
     * Il est possible de créer plusieurs service pour un même device, dans ce cas confer commentaires en fin de methode.
     * @return LocalDevice
     * @throws ValidationException
     * @throws LocalServiceBindingException
     * @throws IOException
     */
	public LocalDevice createDevice()
	        throws ValidationException, LocalServiceBindingException, IOException {
	
		/**
		 * Description du Device
		 */
	    DeviceIdentity identity =
	            new DeviceIdentity(
	                    UDN.uniqueSystemIdentifier("Demo Binary Light")
	            );
	
	    DeviceType type =
	            new UDADeviceType("BinaryLight", 1);
	
	    DeviceDetails details =
	            new DeviceDetails(
	                    "Friendly Binary Light",					// Friendly Name
	                    new ManufacturerDetails(
	                    		"ACME",								// Manufacturer
	    						""),								// Manufacturer URL
	                    new ModelDetails(
	                            "BinLight2000",						// Model Name
	                            "A demo light with on/off switch.",	// Model Description
	                            "v1" 								// Model Number
	                    )
	            );
	
	
	    // Ajout du service
	    LocalService<SwitchPower> switchPowerService =
	            new AnnotationLocalServiceBinder().read(SwitchPower.class);
	
	    switchPowerService.setManager(
	            new DefaultServiceManager(switchPowerService, SwitchPower.class)
	    );
	
	    // retour en cas de 1 service
	    return new LocalDevice(identity, type, details, switchPowerService);
	

		/* Si jamais plusieurs services pour un device (adapter code)
	    return new LocalDevice(
	            identity, type, details, 
	            new LocalService[] {switchPowerService, myOtherService}
	    );
	    */
	}


}