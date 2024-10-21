package blocksworld.planning.goals;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

public interface Goal{
    // Retourne true si le but est satisfait par l'état "state", false sinon
    public boolean isSatisfiedBy(Map<Variable, Object> state);
}