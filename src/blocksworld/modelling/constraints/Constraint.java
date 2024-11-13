package blocksworld.modelling.constraints;

import java.util.Map;
import java.util.Set;
import blocksworld.modelling.variables.Variable;

public interface Constraint {
    public Set<Variable> getScope();
    public boolean isSatisfiedBy(Map<Variable, Object> instanciations);
}
