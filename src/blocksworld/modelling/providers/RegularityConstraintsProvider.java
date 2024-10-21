package blocksworld.modelling.providers;

import blocksworld.modelling.constraints.ConstantGapConstraint;
import blocksworld.modelling.variables.Variable;

public class RegularityConstraintsProvider extends AbstractConstraintsProvider {
    
    public RegularityConstraintsProvider(int blocksCount, int stacksCount) {
        super(blocksCount, stacksCount);
        // création des contraintes de régularité
        for(Variable onB : vm.getOnVariables()){
            for(Variable onB2 : vm.getOnVariables()){
                if(onB.getId() != onB2.getId()) constraints.add(new ConstantGapConstraint(onB, onB2));
            }
        }
    }

}
