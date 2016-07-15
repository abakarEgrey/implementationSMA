package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;
import java.beans.PropertyChangeSupport;

/**
 * Classe Intérupteur, pour allumer une lambe binaire.
 * @author tbille
 *
 */

@UpnpService(
        serviceId = @UpnpServiceId("SwitchPower"),								// Identifiant Unique
        serviceType = @UpnpServiceType(value = "SwitchPower", version = 1)		// Définition de la version
)
public class SwitchPower {
    /**
     * Variable D'Etat, non évenemencée 
     * Permet d'envoyer le message de l'état dans lequel la lampe doit être
     */
    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private boolean target = false;
    
    /**
     * Variable d'etat évenemmencée
     * Permet de vérifier si la lampe est bien dans le bon état.
     */
    @UpnpStateVariable(defaultValue = "0")
    private boolean status = false;
    
    /**
     * Variable qui me permet d'emmettre des évenements UPnP et JavaBean
     */
    private final PropertyChangeSupport propertyChangeSupport;
    public SwitchPower() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Get propertyChangeSupport
     * @return PropertyChangeSupport
     */
    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    /**
     * Permet d'envoyer un message de changement d'etat de la lampe
     * @param newTargetValue
     */
    @UpnpAction
    public void setTarget(@UpnpInputArgument(name = "NewTargetValue") boolean newTargetValue) {

    	// [FACULTATIF] je garde la l'ancienne valeur pour emmettre l'evenenment 
        boolean targetOldValue = target;
        target = newTargetValue;
        
        /*
         * ...
         * Ici on imagine un algorithme qui vérifie que la lampe a bien changé d'état
         * ...
         */
        
        boolean statusOldValue = status;
        status = newTargetValue;

        // Envoie un évenement UPnP, c'est le nom de la variable d'etat qui lance l'évenement
        // COMMENCE PAR UNE MAJUSCULE. Ici "Status" pour la varialbe status
        getPropertyChangeSupport().firePropertyChange("Status", statusOldValue, status);
        // Fonctionne aussi :
        // getPropertyChangeSupport().firePropertyChange("Status", null null);
        
        // [FACULTATIF]
        // Ceci n'a pas d'effet pour le monitoring UPnP, mais fonctionne avec Javabean.
        // Ici on met le nom de la variable : status
        getPropertyChangeSupport().firePropertyChange("status", statusOldValue, status);
    }

    /**
     * Get target of the lamp
     * Methode Upnp grace au système d'annotation
     * @return boolean
     */
    @UpnpAction(out = @UpnpOutputArgument(name = "RetTargetValue"))
    public boolean getTarget() {
        return target;
    }

    /**
     * Get Status of the lamp
     * Methode Upnp grace au système d'annotation
     * @return boolean
     */
    @UpnpAction(out = @UpnpOutputArgument(name = "ResultStatus"))
    public boolean getStatus() {
    	// Pour ajouter des informations supplémentaires UPnP en cas d'erreur :
        // throw new ActionException(ErrorCode.ACTION_NOT_AUTHORIZED);
        return status;
    }
    
    /**
     * Print the version of the code
     * Ceci n'est pas une methode UPnP
     */
    public void printVersion(){
    	System.out.println("Version : 1.0");
    }
}