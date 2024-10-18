package blocksworld.modelling.providers;

import blocksworld.modelling.constraints.SuperiorityConstraint;
import blocksworld.modelling.variables.Variable;

public class GrowingConstraintsProvider extends AbstractConstraintsProvider {
    public GrowingConstraintsProvider(int blocksCount, int stacksCount) {
        super(blocksCount, stacksCount);
        // création des contraintes de croisement
        for(Variable onB : vm.getOnVariables()){
            constraints.add(new SuperiorityConstraint(onB));
        }
    }
}
