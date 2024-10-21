package blocksworld.planning.planners;

import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.variables.Variable;
import blocksworld.planning.actions.Action;
import blocksworld.planning.goals.Goal;

public interface Planner{

    // Retourne un plan calculé en fonction de l'algorithme spécifique de l'implémentation de l'interface
    public List<Action> plan();

    // Accesseur pour l'état initial
    public Map<Variable, Object> getInitialState();

    // Accesseur pour les actions
    public Set<Action> getActions();

    // Accesseur pour le but
    public Goal getGoal();

    // Retourne le nom de l'algorithme utilisé
    public String getAlgo();
}