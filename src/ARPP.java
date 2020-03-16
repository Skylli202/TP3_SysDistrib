import visidia.simulation.process.algorithm.LC0_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class ARPP extends LC0_Algorithm {
    @Override
    public String getDescription(){
        return "Spanning tree algorithm using LC0.\n"
                + "Rule 1 : A---N -> I===A"
                + "Rule 2 : A===I -> F===A {A---N}";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("Arty", );
    }

    @Override
    protected void onStarCenter() {
        if (getLocalProperty("label").equals("A") && getNeighborProperty("label").equals("N")){
            setNeighborProperty("label", "A");
            setLocalProperty("label","I");
            setDoorState(new MarkedState(true),neighborDoor);
        }

        if (getLocalProperty("label").equals("A") && getNeighborProperty("label").equals("I")){
            for (int i = 0; i < getActiveDoor().size(); i++) {

            }
            if(!( getLocalProperty("label").equals("A") && getNeighborProperty("label").equals("N"))){
                setNeighborProperty("label", "A");
                setLocalProperty("label","F");
            }
        }
    }

    @Override
    public Object clone(){
        return new ARPP();
    }
}