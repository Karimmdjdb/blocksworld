package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;
import util.ClassComparator;

public class NonOverlappingConstraint extends BinaryConstraint{

    public NonOverlappingConstraint(Variable v1, Variable v2) {
        super(v1, v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiations) {
        for(Variable var : getScope()){
            if(!instantiations.containsKey(var)) throw new IllegalArgumentException();
        }
        return instantiations.get(v1).equals(instantiations.get(v2));
    }

    @Override
    public boolean equals(Object other) {
        if(ClassComparator.hardComparaison(this, other)){
            NonOverlappingConstraint casterOther = (NonOverlappingConstraint) other;
            return (v1.equals(casterOther.v1) || v1.equals(casterOther.v2)) && (v2.equals(casterOther.v1) || v2.equals(casterOther.v2));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return v1.hashCode() + v2.hashCode();
    }

    
    
}
