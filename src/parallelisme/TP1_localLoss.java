package parallelisme;

import visidia.simulation.process.algorithm.LC2_Algorithm;

import java.util.ArrayList;

public class TP1_localLoss extends LC2_Algorithm {
    @Override
    public Object clone() {
        return new TP1_localLoss();
    }

    @Override
    public String getDescription() {
        String str = "";
        str = "[TP1_2] Exercice 2: pallier la perte d’une information locale";
        return str;
    }

    @Override
    protected void beforeStart() {
        /**
         * LC2: Je peux modifier mes voisins
         */
        int syncCount = 0;

        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("id", vertex.getId());
        setLocalProperty("activeNeighboors", getArity());
    }

    @Override
    protected void onStarCenter() {
        /**
         * Chaque nœud enregistre les identifiants et les étiquettes de tous ses voisins, à l’exception de celui qui se trouve sur le port 0.
         */
        ArrayList<Object> idList = new ArrayList<>(getActiveDoors().size());
        ArrayList<Object> labelList = new ArrayList<>(getActiveDoors().size());

        for (int i = 1; i < getActiveDoors().size(); i++) {
            // i start at 1 in order to do not record id & label of neighboors port 0
            idList.set(i, (Object) getNeighborProperty(i, "label"));
            labelList.set(i, (Object) getNeighborProperty(i, "id"));
        }

        /**
         *  Au bout de trois synchronisation, le nœud ayant pour étiquette “A” s’arrête.
         */

        if ((syncCount >= 3) && (getLocalProperty("label").equals("A"))) {

        }

    }
}
