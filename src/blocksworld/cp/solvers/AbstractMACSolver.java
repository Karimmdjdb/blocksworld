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
        Map<Variable, Set<Object>> domainsEvo = new HashMap<>();
        for(Variable v : variables) domainsEvo.put(v, new HashSet<>(v.getDomain()));

        // filatrage et réduction des domaines avant le début effectif la recherche par arc cohérence.
        ArcConsistency ac = new ArcConsistency(constraints);
        if(!ac.ac1(domainsEvo)) return null;

        // appel à la méthone auxiliaire (recherche).
        return helper(new HashMap<>(), new LinkedList<>(variables), domainsEvo);
    }

    protected abstract Map<Variable, Object> helper(Map<Variable, Object> partInstanciation, LinkedList<Variable> uninstanciatedVars, Map<Variable, Set<Object>> domainsEvo);
}
