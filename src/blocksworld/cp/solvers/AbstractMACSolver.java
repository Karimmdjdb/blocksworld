package blocksworld.cp.solvers;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;

import blocksworld.modelling.variables.Variable;
import blocksworld.modelling.constraints.Constraint;

// Solveur de CSP qui utilise l'algorithme de Maintien de Cohérence d'Arc.
public abstract class AbstractMACSolver extends AbstractSolver {

    public AbstractMACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    @Override
    public Map<Variable, Object> solve() {
        // création de la variable d'évolution de domaine.
        Map<Variable, Set<Object>> domains = new HashMap<>();
        for(Variable v : variables) domains.put(v, new HashSet<>(v.getDomain()));
        // appel à la méthone auxiliaire.
        return helper(new HashMap<>(), new LinkedList<>(variables), domains);
    }

    protected abstract Map<Variable, Object> helper(Map<Variable, Object> partInstanciation, LinkedList<Variable> uninstanciatedVars, Map<Variable, Set<Object>> domainsEvo);
}
