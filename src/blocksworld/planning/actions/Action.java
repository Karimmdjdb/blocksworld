package blocksworld.planning.actions;

import java.util.Map;
import blocksworld.modelling.variables.Variable;

public interface Action {
    // Renvoie true si l'action est appliquable, false sinon
    public boolean isApplicable(Map<Variable, Object> state);

    // Applique l'action à un état et renvoie l'état suivant
    public Map<Variable, Object> successor(Map<Variable, Object> state);

    // Accesseur pour le cout de l'action
    public int getCost();
}