import visidia.rule.Neighbor;
        import visidia.simulation.SimulationConstants;
        import visidia.simulation.process.algorithm.LC2_Algorithm;
        import visidia.simulation.process.edgestate.MarkedState;

public class TP5_ClosedStar_Election extends LC2_Algorithm {
    @Override
    public Object clone() {
        return new TP5_ClosedStar_Election();
    }

    @Override
    public String getDescription() {
        return "Election etoile fermé";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("activeNeighboors", getArity());
    }

    @Override
    protected void onStarCenter() {
        // We are in a closed star to I can modify my neighboors

        // Election
        if(getLocalProperty("label").equals("N")){
            for (int i = 0; i < getActiveDoors().size(); i++) {
                if(getNeighborProperty(i,"label").equals("N") && ((int)(getNeighborProperty(i,"activeNeighboors")) == 1)){
                    setNeighborProperty(i, "label", "F");
                    setLocalProperty("activeNeighboors", (((int)getLocalProperty("activeNeighboors")) -1));
                }
            }

            if(((int)(getLocalProperty("activeNeighboors")) == 0)){
                setLocalProperty("label", "E");
            }
        }
        putProperty("Affichage", getLocalProperty("activeNeighboors"), SimulationConstants.PropertyStatus.DISPLAYED);
    }
}
