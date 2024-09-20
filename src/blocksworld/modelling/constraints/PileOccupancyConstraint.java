package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;

public class PileOccupancyConstraint extends BinaryConstraint{

    public PileOccupancyConstraint(Variable v1, Variable v2) {
        super(v1, v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiations) {
        for(Variable var : getScope()){
            if(!instantiations.containsKey(var)) throw new IllegalArgumentException();
        }
        return instantiations.get(v1).equals(v2.getId()) && !(Boolean)instantiations.get(v2);
    }

    
    
}
