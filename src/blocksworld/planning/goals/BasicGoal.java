package blocksworld.planning.goals;

import java.util.Map;
import blocksworld.modelling.variables.Variable;

public class BasicGoal implements Goal{

    private Map<Variable, Object> partialInstanciation;

    public BasicGoal(Map<Variable, Object> partialInstanciation){
        this.partialInstanciation = partialInstanciation;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> state){
        for(Variable v : partialInstanciation.keySet()){
            if(!state.containsKey(v) || !state.get(v).equals(partialInstanciation.get(v))) return false;
        }
        return true;
    }

    public Map<Variable, Object> getPartInstanciation() {
        return partialInstanciation;
    }

    @Override
    public String toString() {
        return partialInstanciation.toString();
    }
}