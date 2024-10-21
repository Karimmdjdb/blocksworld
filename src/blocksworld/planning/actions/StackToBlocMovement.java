package blocksworld.planning.actions;

import java.util.Map;

import blocksworld.modelling.variables.FixedVariable;
import blocksworld.modelling.variables.FreeVariable;
import blocksworld.modelling.variables.OnVariable;

public class StackToBlocMovement extends Movement {
    public StackToBlocMovement(int subject, int from, int to) {
        super();
        precondition = Map.of(
            new OnVariable(subject, Movement.globalOnDomain), from,
            new FixedVariable(subject), false,
            new FixedVariable(to), false
        );
        effect = Map.of(
            new OnVariable(subject, Movement.globalOnDomain), to,
            new FreeVariable(from), true,
            new FixedVariable(to), true
        );
    }
}
