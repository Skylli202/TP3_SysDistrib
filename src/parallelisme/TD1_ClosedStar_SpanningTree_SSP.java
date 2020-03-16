package parallelisme;

import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class TD1_ClosedStar_SpanningTree_SSP extends LC2_Algorithm {

    @Override
    public Object clone() {
        return new TD1_ClosedStar_SpanningTree_SSP();
    }

    @Override
    public String getDescription() {
        String res = "Etoile fermer + SSP";
        return res;
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("linked", false);

        setLocalProperty("a", -1);
        setLocalProperty("p", false);

    }

    @Override
    protected void onStarCenter() {

        // Arbre Recouvrant
        if(getLocalProperty("label").equals("A")){
            for (int i = 0; i < getActiveDoors().size(); i++) {
                if(getNeighborProperty(i,"label").equals("N")){
                    setNeighborProperty(i, "label", "A");
                    setDoorState(new MarkedState(true),i);
                }
            }
        }

        // Detection de la terminaison local
        if(getLocalProperty("label").equals("A")){
            boolean localStop = true;
            for (int i = 0; i < getActiveDoors().size(); i++) {
                if(getNeighborProperty(i, "label").equals("N")){
                    localStop = false;
                }
            }

            if(localStop){
                setLocalProperty("label", "W");
                setLocalProperty("p", true);
            }
        }

        // Algorithm SSP
        if((boolean)getLocalProperty("p")) {
            int minA = (int) getLocalProperty("a");

            for (int i = 0; i < getActiveDoors().size(); i++) {
                if((int) getNeighborProperty(i,"a") < minA){
                    minA = (int) getNeighborProperty(i,"a");
                }
            }

            setLocalProperty("a", (minA+1));
        }


        putProperty("affichage_a", getLocalProperty("a"), SimulationConstants.PropertyStatus.DISPLAYED);
        putProperty("affichage_p", getLocalProperty("p"), SimulationConstants.PropertyStatus.DISPLAYED);
    }
}
