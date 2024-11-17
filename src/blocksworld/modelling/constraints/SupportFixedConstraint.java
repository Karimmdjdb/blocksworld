package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

// Classe qui représente la contrainte On(b) = b' → Fixed(b) = true
public class SupportFixedConstraint extends BinaryConstraint{

    public SupportFixedConstraint(Variable v1, Variable v2) {
        super(v1, v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        checkIfScopeIsTreated(instanciation);
        return !instanciation.get(v1).equals(v2.getId()) || (Boolean)instanciation.get(v2);
    }
}
