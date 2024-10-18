package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

public class SuperiorityConstraint extends UnaryConstraint {
    public SuperiorityConstraint(Variable v) {
        super(v);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciations) {
        checkIfScopeIsTreated(instanciations);
        return v.getId() > (int)instanciations.get(v);
    }
}
