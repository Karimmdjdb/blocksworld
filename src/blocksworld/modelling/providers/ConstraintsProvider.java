package blocksworld.modelling.providers;

import java.util.Set;
import blocksworld.modelling.constraints.Constraint;

public interface ConstraintsProvider {
    public Set<Constraint> getConstraints();
}
