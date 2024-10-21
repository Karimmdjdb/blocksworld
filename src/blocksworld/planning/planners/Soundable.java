package blocksworld.planning.planners;

// Interface pour une entité sondable
public interface Soundable {

    // active la sonde
    public void activateNodeCount(boolean activate);

    // retourne le nombre de noeuds explorés
    public int getNodeCount();
}
