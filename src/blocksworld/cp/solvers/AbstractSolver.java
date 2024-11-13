package blocksworld.cp.solvers;

import java.util.Set;
import java.util.Map;
import blocksworld.modelling.variables.Variable;
import blocksworld.modelling.constraints.Constraint;


// Classe qui serviera de base pour nos différents solveurs de CSP.
public abstract class AbstractSolver implements Solver {

    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
    }

    // Vérifie si une instantiation est consistante (satisfait toutes les contraintes).
    public boolean isConsistent(Map<Variable, Object> partInstaciation){
        for(Constraint c : this.constraints){
            if(partInstaciation.keySet().containsAll(c.getScope()))
            if(!c.isSatisfiedBy(partInstaciation)) return false;
        }
        return true;
    }
}
