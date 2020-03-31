package parallelisme;

import visidia.simulation.process.algorithm.LC2_Algorithm;

import java.util.ArrayList;

public class TP1_localLoss extends LC2_Algorithm {

    private int syncCount;
    private String[] labelTab;
    private int[] idTab;

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

        setLocalProperty("label", vertex.getLabel());
        setLocalProperty("id", vertex.getId());
        setLocalProperty("activeNeighboors", getArity());

        // init attribute
        syncCount = 0;
        labelTab = new String[getArity()];
        idTab = new int[getArity()];
    }

    @Override
    protected void onStarCenter() {
        /**
         *  Au bout de trois synchronisation, le nœud ayant pour étiquette “A” s’arrête.
         */

        if ((syncCount >= 3) && (getLocalProperty("label").equals("A"))) {
            if(syncCount == 3) {
                // in order to not spam command line I display this only once...
                System.out.println("syncCount is equal to 3, node in \"A\" state stop (local termination...)");
            }
            this.localTermination();
        }

        /**
         * Chaque nœud enregistre les identifiants et les étiquettes de tous ses voisins, à l’exception de celui qui se trouve sur le port 0.
         */
        // get done what is ask to be done (yeah ... \o/)
        for (int i = 0; i < getActiveDoors().size(); i++) {
            int numPort = getActiveDoors().get(i);
            if(numPort != 0) {
                // exclusion of port 0 because I shoudn't access to it...
                labelTab[numPort] = (String) getNeighborProperty(numPort, "label");
                idTab[numPort] = (int) getNeighborProperty(numPort, "id");
            }
        }

        /**
         * Question 1 : Comment peut-on détecter cet arrêt (au niveau des nœuds voisins) ?
         *      On le détecte avec le faites que getActivesDoors perd un voisin
         *          -> comparer la getArity() et la taille de la liste getActivesDoors()
         *             en toute logique si jamais un voisin est arrêté (localTermination()),
         *             il sera exclus de la liste getActivesDoors() qui sera alors plus petite que get Arity()
         *
         */

        if(getArity() > getActiveDoors().size()) {
            // if it does enter here, it means that there is a local loss somewhere...
            System.out.println("local loss happened somewhere...");

            /**
             * Question 2 : Faites en sorte que l’étiquette du nœud arrêté soit connue par son voisin qui l’ignorait (car “A” était
             * sur le port 0 de ce voisin).
             *      Une fois la détection de l'arrêt d'un noeuds voisins (cf. Q1) alors si aucun de mes voisins (port 0 exclus) est a l'état "A",
             *      alors c'est que mon voisin sur le port 0 est l'état "A".
             */

            String str = "";
            for (int i = 0; i < labelTab.length; i++) {
                str += labelTab[i];
            }

            if(!str.contains("A")){
                // code here are for node that have node in "A" state in port 0
                // exec code ...
                System.out.println("my id is " + getLocalProperty("id") +" and the node connected to my port 0 is in \"A\" state");
                printData();
            }
        }

        /**
         * Question 3 :
         *      a) La récupération de l’information à la question 2 est-elle garantie ?
         *              La récupration de l'information n'est pas garantie.
         *
         *      b) Si oui, pourquoi ?
         *         Sinon expliquez les raisons qui pourraient empêcher cette récupération (ou décrivez un scénario rendant cette récupération impossible).
         *              Lorsque plusieurs pertes local compromettent l'intégrité du graphe (cf. schema) alors,
         *              la récupération d'information devient alors impossible pour le noeud isolé (par les pertes locals)
         */

        // A la fin d'une synchronisation, j'incrémente syncCount
        syncCount++;
    }

    public void printData(){
        if(idTab.length != labelTab.length){
            // error...
        } else {
            for (int i = 0; i < idTab.length; i++) {
                System.out.println("myid["+ getLocalProperty("id") +"] port[\""+ i +"\"] - id["+ idTab[i]+"] - label :\"" + labelTab[i]+"\"");
            }
        }
    }
}
