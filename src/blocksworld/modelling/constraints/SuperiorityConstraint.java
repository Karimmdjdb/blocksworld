package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

// Classe qui représente la contrainte b > On(b)
public class SuperiorityConstraint extends UnaryConstraint {
    public SuperiorityConstraint(Variable v) {
        super(v);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        checkIfScopeIsTreated(instanciation);
        return v.getId() > (int)instanciation.get(v);
    }
}
