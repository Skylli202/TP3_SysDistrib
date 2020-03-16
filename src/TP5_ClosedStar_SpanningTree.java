import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class TP5_ClosedStar_SpanningTree extends LC2_Algorithm {
    @Override
    public Object clone() {
        return new TP5_ClosedStar_SpanningTree();
    }

    @Override
    public String getDescription() {
        return "arbre recouvrant etoile fermé";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("activeNeighboors", getArity());
    }

    @Override
    protected void onStarCenter() {
        // We are in a closed star to I can modify my neighboors
        // Spanning Tree
        if(getLocalProperty("label").equals("A")){
            for (int i = 0; i < getActiveDoors().size(); i++) {
                if(!getNeighborProperty(i,"label").equals("A")){
                    setNeighborProperty(i, "label", "A");
                    setDoorState(new MarkedState(true),i);
                }
            }
        }
    }
}
