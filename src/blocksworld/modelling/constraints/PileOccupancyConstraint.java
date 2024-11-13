package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

public class PileOccupancyConstraint extends BinaryConstraint{

    public PileOccupancyConstraint(Variable v1, Variable v2) {
        super(v1, v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciations) {
        checkIfScopeIsTreated(instanciations);
        return instanciations.get(v1).equals(v2.getId()) && !(Boolean)instanciations.get(v2);
    }
}
