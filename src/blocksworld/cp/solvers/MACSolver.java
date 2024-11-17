package blocksworld.cp.solvers;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedList;

import blocksworld.modelling.variables.Variable;
import blocksworld.modelling.constraints.Constraint;

// Solveur de CSP qui utilise l'algorithme de Maintien de Cohérence d'Arc.
public class MACSolver extends AbstractMACSolver {

    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    @Override
    protected Map<Variable, Object> helper(Map<Variable, Object> partInstanciation, LinkedList<Variable> uninstanciatedVars, Map<Variable, Set<Object>> domainsEvo) {
        if(uninstanciatedVars.isEmpty()) return partInstanciation;

        // recherche d'une solution.
        Variable v = uninstanciatedVars.poll(); // choix d'une variable non instanciée (on prend juste la premèire).

        for(Object value : domainsEvo.get(v)) { // pour chaque valeur du domaine de la valeur seléctionnée.
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
        return "MAC";
    }
}
