package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

public class ConstantGapConstraint extends BinaryConstraint {
    
    public ConstantGapConstraint(Variable v1, Variable v2) {
        super(v1, v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciations) {
        checkIfScopeIsTreated(instanciations);
        return !(instanciations.get(v1).equals(v2.getId()) && ((int)instanciations.get(v2) >= 0)) || v1.getId()-v2.getId() == v2.getId()-(Integer)instanciations.get(v2); 
    }
}
