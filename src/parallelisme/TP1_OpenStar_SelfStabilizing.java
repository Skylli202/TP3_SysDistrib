package parallelisme;

import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

import java.sql.Array;
import java.util.ArrayList;

public class TP1_OpenStar_SelfStabilizing extends LC1_Algorithm {

    @Override
    public Object clone() {
        return new TP1_OpenStar_SelfStabilizing();
    }

    @Override
    public String getDescription() {
        /**
         *  Complément information : Peut representer l'acces de différents client à différents serveur. On veux acceder
         *      à différentes ressources et on ne veut pas acceder au même serveur en même temps qu'un de nos voisins.
         *
         *  ( 1 ) - Quel est le type de synchronisation le mieux indiqué pour l’écriture de ces règles ?
         *              La synchronisation en étoile ouverte est la plus adapté à ces règles,
         *          nous n'avons pas besoin de modifier les états de nos voisins, juste de connaitre
         *          dans un même instant à leurs états.
         *  ( 2 ) - Les sommets doivent-ils, au départ, avoir obligatoirement l’une des trois couleurs choisies ?
         *              Oui, dans notre cas pour éviter dans ViSiDiA un bloquage de notre algorithme, cependant en
         *              condition réelle, on a pas besoin de partir du même état puisque dans l'exemple de l'accès
         *              aux ressources, la demande d'accès à une ressource dépends du de l'initiateur de la demande.
         *
         *
         */
        return  "Auto-stabilisation en étoile ouverte (LC1)\n" +
                "Rule 1 : X --- X --- X ---> X --- Y --- X \n" +
                "Rule 2 : X --- X --- Y ---> X --- Z --- Y \n";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("linked", false);
    }

    @Override
    protected void onStarCenter() {
        /**
         * LC1 == Je ne peux modifier que MON état et ne peux que lire l'état de mes voisins.
         */

        String statePossibility[] = {"N","Y","Z"};
        ArrayList<String> neightboorsState = new ArrayList<>();

        for (int i = 0; i < getActiveDoors().size(); i++) {
            int numPort = getActiveDoors().get(i);
            neightboorsState.add((String) getNeighborProperty(numPort, "label"));
        }

        // Rule 1 & 2
        if(neightboorsState.contains(getLocalProperty("label"))) {
            for (String state : statePossibility)
                if(!neightboorsState.contains(state))
                    setLocalProperty("label",state);
        }
    }
}
