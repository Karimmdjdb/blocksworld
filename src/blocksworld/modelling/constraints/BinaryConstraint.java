package blocksworld.modelling.constraints;

import java.util.HashSet;
import java.util.Set;

import blocksworld.modelling.variables.Variable;

public abstract class BinaryConstraint implements Constraint{
    
    protected Variable v1, v2;

    public BinaryConstraint(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);
        return scope;
    }

    @Override
    public String toString() {
        return String.format("{%s : v1=%s, v2=%s}\n", this.getClass().getSimpleName(), v1.toString(), v2.toString());
    }
    
}
