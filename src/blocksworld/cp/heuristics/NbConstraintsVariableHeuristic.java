package blocksworld.cp.heuristics;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import blocksworld.modelling.variables.Variable;
import blocksworld.modelling.constraints.Constraint;

// Heuristique sur le nombre d'aparition des variables dans les contraintes.
public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    private Set<Constraint> constraints;
    private boolean mostConstraintsAppearence;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean mostConstraintsAppearence) {
        this.constraints = constraints;
        this.mostConstraintsAppearence = mostConstraintsAppearence;
    }

    // Retourne la variable qui est concernée par le plus de contraintes (si mostConstraintsAppearence = true),
    // ou la variable concernée par le moins de contraintes (si mostConstraintsAppearence = false).
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable best = null;
        int max = 0, min = Integer.MAX_VALUE;
        Map<Variable, Integer> counts = new HashMap<>();
        for(Variable var : variables) {
            for(Constraint cons : constraints) {
                if(cons.getScope().contains(var)) counts.put(var, (counts.get(var) != null ? counts.get(var) + 1 : 1));
            }
            int count = counts.get(var);
            if(mostConstraintsAppearence) {
                if(count > max) {
                    max = count;
                    best = var;
                }
            }
            else {
                if(count < min) {
                    min = count;
                    best = var;
                }
            }
        }
        return best;
    }
}
