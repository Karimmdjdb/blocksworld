package blocksworld.planning.heuristics;

import java.util.Map;

import blocksworld.modelling.variables.OnVariable;
import blocksworld.modelling.variables.Variable;
import blocksworld.planning.goals.BasicGoal;
import blocksworld.planning.goals.Goal;

public class MisplacedHeuristic implements Heuristic {
    private Goal goal;

    public MisplacedHeuristic(Goal goal) {
        this.goal = goal;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        int cost = 0;
        for(Map.Entry<Variable, Object> entry : ((BasicGoal)goal).getPartInstanciation().entrySet()) {
            Variable var = entry.getKey();
            if(!(var instanceof OnVariable)) continue;
            Object val = entry.getValue();
            if(!state.get(var).equals(val)) cost++;
        }
        return cost;
    }

    
}
