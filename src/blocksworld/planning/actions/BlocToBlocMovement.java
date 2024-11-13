package blocksworld.planning.actions;

import java.util.Map;

import blocksworld.modelling.variables.FixedVariable;
import blocksworld.modelling.variables.OnVariable;

public class BlocToBlocMovement extends Movement {

    public BlocToBlocMovement(int subject, int from, int to) {
        super();
        precondition = Map.of(
            new OnVariable(subject, Movement.globalOnDomain), from,
            new FixedVariable(subject), false,
            new FixedVariable(to), false
        );
        effect = Map.of(
            new OnVariable(subject, Movement.globalOnDomain), to,
            new FixedVariable(from), false,
            new FixedVariable(to), true
        );
    }
}
