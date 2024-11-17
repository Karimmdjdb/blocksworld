package blocksworld.cp.solvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.variables.Variable;
import blocksworld.cp.heuristics.ValueHeuristic;
import blocksworld.cp.heuristics.VariableHeuristic;
import blocksworld.modelling.constraints.Constraint;

// Solveur de CSP qui utilise l'algorithme de Maintien de Cohérence d'Arc et des heuristiques de variable et valeurs.
public class HeuristicMACSolver extends AbstractMACSolver {

    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    @Override
    protected Map<Variable, Object> helper(Map<Variable, Object> partInstanciation, LinkedList<Variable> uninstanciatedVars, Map<Variable, Set<Object>> domainsEvo) {
        if(uninstanciatedVars.isEmpty()) return partInstanciation;

        // recherche d'une solution.
        Variable v = variableHeuristic.best(new HashSet<>(uninstanciatedVars), domainsEvo);  // séléction de la meilleure variable au sens de l'heuristique de variable utilisée.
        uninstanciatedVars.remove(v);

        for(Object value : valueHeuristic.ordering(v, domainsEvo.get(v))) { // pour chaque valeur du domaine ordonné au sens de l'heuristique de valeurs utilisée.
            // on l'ajoute à l'instanciation partielle.
            Map<Variable, Object> newInstanciation = new HashMap<>(partInstanciation);
            newInstanciation.put(v, value);
            if(isConsistent(newInstanciation)) { // si la nouvelle instanciation est consistante on la garde et on continue.
                Map<Variable, Object> next = helper(newInstanciation, uninstanciatedVars, domainsEvo);
                if(next != null) return next;
            }
        }
        // sinon on met fin à la recherche.
        uninstanciatedVars.offer(v);
        return null;
    }

        @Override
        public String getAlgo() {
            return "MAC (heuristic)";
        }
}