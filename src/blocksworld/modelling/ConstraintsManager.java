package blocksworld.modelling;

import java.util.Set;
import java.util.HashSet;
import blocksworld.modelling.constraints.Constraint;
import blocksworld.modelling.constraints.NonOverlappingConstraint;
import blocksworld.modelling.constraints.PileOccupancyConstraint;
import blocksworld.modelling.constraints.SupportFixedConstraint;
import blocksworld.modelling.variables.Variable;

public class ConstraintsManager {
    private VariablesManager vm;
    private Set<Constraint> constraints = new HashSet<>();

    public ConstraintsManager(int blocksCount, int stacksCount){
        vm = new VariablesManager(blocksCount, stacksCount);
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

    public Set<Constraint> getConstraints(){
        return constraints;
    }
}
