package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

// Classe qui représente la contrainte On(b) = b' ∧ On(b') = b'' → b - b' = b' - b''
public class ConstantGapConstraint extends BinaryConstraint {
    public ConstantGapConstraint(Variable v1, Variable v2) {
        super(v1, v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        checkIfScopeIsTreated(instanciation);
        return !(instanciation.get(v1).equals(v2.getId()) && ((int)instanciation.get(v2) >= 0)) || v1.getId()-v2.getId() == v2.getId()-(Integer)instanciation.get(v2); 
    }
}
