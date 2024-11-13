package blocksworld.cp.heuristics;

import java.util.Map;
import java.util.Set;
import blocksworld.modelling.variables.Variable;


// Interface pour les Heuristiques de variable.
public interface VariableHeuristic {
    // Retourne la meilleure variable au sens de l'heuristique.
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains);
}
