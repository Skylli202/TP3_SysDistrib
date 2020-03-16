/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jmddu
 */

import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC0_Algorithm;

public class TP3_HandShake_Election extends LC0_Algorithm {
    
    //private int neighboorsActiveCpt = 1;

    @Override
    public String getDescription(){
        return "Spanning tree algorithm using LCO.\n" +
                "Rule 1 : N(1) --- N(x) ---> F(0) --- N(x-1) \n" +
                "Rule 2 : N(1) --- N(1) ---> E(0) --- F(0) \n";
    }
    
    @Override
    protected void beforeStart(){
        setLocalProperty("label", vertex.getLabel());
        //this.neighboorsActiveCpt = getArity();
        //setLocalProperty("activeNeighboors", neighboorsActiveCpt);
        setLocalProperty("activeNeighboors", getArity());

    }

    @Override
    protected void onStarCenter (){
        if (this.getLocalProperty("label").equals("F")) {
            this.localTermination();
        }

        if(getLocalProperty("label").equals("N") &&
                getNeighborProperty("label").equals("N")) {
            if (((int) getLocalProperty("activeNeighboors")) == 1 && ((int) getNeighborProperty("activeNeighboors")) > 1) {
                setLocalProperty("label", "F");
                setLocalProperty("activeNeighboors", 0);
                setNeighborProperty("activeNeighboors", ((int) getNeighborProperty("activeNeighboors")) - 1);

            } else if (((int) getLocalProperty("activeNeighboors")) == 1 && ((int) getNeighborProperty("activeNeighboors")) == 1) {
                setNeighborProperty("label", "F");
                setNeighborProperty("activeNeighboors", 0);

                setLocalProperty("label", "E");
                setLocalProperty("activeNeighboors", 0);
            }
        }
        if(getLocalProperty("label").equals("E")) {
            //globalTermination();
        }
        putProperty("Affichage", getLocalProperty("activeNeighboors"), SimulationConstants.PropertyStatus.DISPLAYED);
    }
    
    @Override
    public Object clone(){
        return new TP3_HandShake_Election();
    }
    
}
