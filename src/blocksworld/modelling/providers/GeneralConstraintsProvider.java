package blocksworld.modelling.providers;

import blocksworld.modelling.constraints.NonOverlappingConstraint;
import blocksworld.modelling.constraints.PileOccupancyConstraint;
import blocksworld.modelling.constraints.SupportFixedConstraint;
import blocksworld.modelling.variables.Variable;

public class GeneralConstraintsProvider extends AbstractConstraintsProvider{

    public GeneralConstraintsProvider(int blocksCount, int stacksCount){
        super(blocksCount, stacksCount);
        for(Variable onB : vm.getOnVariables()){
            // création des contraintes de non-chevauchement
            for(Variable onB2 : vm.getOnVariables()){
                if(onB.getId() != onB2.getId()) constraints.add(new NonOverlappingConstraint(onB, onB2));
            }
            // création des contraintes de support fixe
            for(Variable fixedB2 : vm.getFixedVariables()){
                if(onB.getId() != fixedB2.getId()) constraints.add(new SupportFixedConstraint(onB, fixedB2));
            }
            // création des contraintes de bloquage de piles
            for(Variable freeP : vm.getFreeVariables()){
                constraints.add(new PileOccupancyConstraint(onB, freeP));
            }
        }
    }
}
