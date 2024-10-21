package blocksworld.planning.actions;

import java.util.Map;

import blocksworld.modelling.variables.FixedVariable;
import blocksworld.modelling.variables.FreeVariable;
import blocksworld.modelling.variables.OnVariable;

public class BlocToStackMovement extends Movement {
    public BlocToStackMovement(int subject, int from, int to) {
        super();
        precondition = Map.of(
            new OnVariable(subject, Movement.globalOnDomain), from,
            new FixedVariable(subject), false,
            new FreeVariable(to), true
        );
        effect = Map.of(
            new OnVariable(subject, Movement.globalOnDomain), to,
            new FixedVariable(from), false,
            new FreeVariable(to), false
        );
    }
}
