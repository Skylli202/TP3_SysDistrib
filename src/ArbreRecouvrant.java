import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class ArbreRecouvrant extends LC1_Algorithm {

    @Override
    public Object clone() {
        return new ArbreRecouvrant();
    }

    @Override
    public String getDescription() {
        return  "recover tree algorithm using LC1\n" +
                "Rule 1 : N --- N --- A ---> N --- A === A \n";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("linked", false);
    }

    @Override
    protected void onStarCenter() {
        /**
         * LC1 == Je ne peux changer que MON label
         */

        boolean shouldStop = true;
        for (int i = 0; i < getActiveDoors().size(); i++) {
            int numPort = getActiveDoors().get(i);
            if(getNeighborProperty(numPort, "label").equals("N")){
                shouldStop = false;
            }
        }

        if (shouldStop) {
            this.localTermination();
        }

        for (int i = 0; i < getActiveDoors().size(); i++) {
            int numPort = getActiveDoors().get(i);
            //System.out.println("L'état du voisoin connecté sur le port " + numPort + "est : " + getNeighborProperty(numPort, "label"));
            if(getLocalProperty("label").equals("N") && getNeighborProperty( i,"label").equals("A")) {
                setLocalProperty("label", "A");
                setDoorState(new MarkedState(true),i);
                break;
            }
        }
    }
}
