package blocksworld.planning.providers;

import java.util.HashSet;
import java.util.Set;

import blocksworld.planning.actions.Action;
import blocksworld.planning.actions.BlocToBlocMovement;
import blocksworld.planning.actions.BlocToStackMovement;
import blocksworld.planning.actions.Movement;
import blocksworld.planning.actions.StackToBlocMovement;
import blocksworld.planning.actions.StackToStackMovement;

public class MovementsProvider implements ActionsProvider {
    private Set<Action> movements;

    public MovementsProvider(int blocksCount, int stacksCount) {
        Movement.setGlobalOnDomain(blocksCount, stacksCount);
        Set<Integer> ids = new HashSet<>();
        for(int i = -stacksCount; i<0; i++){
            ids.add(i);
        }
        for(int i = 0; i < blocksCount; i++){
            ids.add(i);
        }
        this.movements = new HashSet<>();
        for(Integer subject : ids) {
            if(subject<0) continue;
            for(Integer from : ids) {
                if(subject == from) continue;
                for(Integer to : ids) {
                    if(subject == to || from == to) continue;
                    Action action;
                    if(from >= 0) { // si la position initiale est un bloc
                        if(to >= 0) { // si la destination est un bloc
                            action = new BlocToBlocMovement(subject, from, to);
                        } else { // si la destination est une pile
                            action = new BlocToStackMovement(subject, from, to);
                        }
                    } else{ // si la position initiale est une pile
                        if(to >= 0) { // si la destination est un bloc
                            action = new StackToBlocMovement(subject, from, to);
                        } else { // si la destination est une pile
                            action = new StackToStackMovement(subject, from, to);
                        }
                    }
                    movements.add(action);
                }
            }
        }
    }

    @Override
    public Set<Action> getActions() {
        return movements;
    }

    
}
