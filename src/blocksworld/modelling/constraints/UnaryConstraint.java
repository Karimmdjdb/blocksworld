package blocksworld.modelling.constraints;

import blocksworld.modelling.variables.Variable;
import java.util.Set;

public abstract class UnaryConstraint extends AbstractConstraint {
    protected Variable v;

    public UnaryConstraint(Variable v) {
        this.v = v;
    }

    @Override
    public Set<Variable> getScope() {
        return Set.of(v);
    }
}
