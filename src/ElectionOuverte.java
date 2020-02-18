/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MOI
 */

import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC1_Algorithm;

public class ElectionOuverte extends LC1_Algorithm {

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
        setLocalProperty("activeNeighboors", getArity());
    }

    @Override
    protected void onStarCenter (){
        if(getLocalProperty("label") == "E")
            globalTermination();

        for (int i = 0; i < getActiveDoors().size(); i++) {
            int numPort = getActiveDoors().get(i);


            if (((int) getLocalProperty("activeNeighboors")) == 1 && ((int) getNeighborProperty(i,"activeNeighboors")) > 1) {
                setLocalProperty("label", "F");
                setLocalProperty("activeNeighboors", ((int) getLocalProperty("activeNeighboors")) - 1);
            }

            boolean shouldStopN = true;
            for (int j = 0; j < getActiveDoors().size(); j++) {
                if(((int) getNeighborProperty(j, "activeNeighboors")) > 1){
                    shouldStopN = false;
                }
            }

            if(shouldStopN){
                setLocalProperty("label","E");
            }
        }
        putProperty("Affichage", getLocalProperty("activeNeighboors"), SimulationConstants.PropertyStatus.DISPLAYED);
    }

    @Override
    public Object clone(){
        return new Election();
    }

}
