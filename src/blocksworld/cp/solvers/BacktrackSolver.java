package blocksworld.cp.solvers;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import blocksworld.modelling.variables.Variable;
import blocksworld.modelling.constraints.Constraint;


// Solveur de CSP qui utilise l'algorithme de BackTracking.
public class BacktrackSolver extends AbstractSolver {

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }


    @Override
    public Map<Variable, Object> solve() {
        // appel à la méthode auxiliaire.
        return bt(new HashMap<>(), new ArrayList<>(variables));
    }

    private Map<Variable, Object> bt(Map<Variable, Object> partInstanciation, List<Variable> uninstanciatedVars) {
        if(uninstanciatedVars.isEmpty()) return partInstanciation;

        // récupération d'une variable disponible.
        Variable var = uninstanciatedVars.remove(0);

        // itération sur les valeurs possibles.
        for(Object value : var.getDomain()) {
            // on crée une nouvelle instanciation = partInstanciation U (var : value).
            Map<Variable, Object> newInstanciation = new HashMap<>(partInstanciation);
            newInstanciation.put(var, value);

            if(isConsistent(newInstanciation)) { // si la nouvelle instanciation est consistante on continue à explorer.
                Map<Variable, Object> next = bt(newInstanciation, uninstanciatedVars);
                if(next != null) return next;
            }
        }
        // dans le cas ou aucune valeur n'a donné une instantiation satisfaisante on met fin à la recherche.
        uninstanciatedVars.add(var);
        return null;
    }

    @Override
    public String getAlgo() {
        return "Backtrack";
    }
}
