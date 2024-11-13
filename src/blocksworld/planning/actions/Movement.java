package blocksworld.planning.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.variables.Variable;

public abstract class Movement implements Action {

    protected Map<Variable, Object> precondition, effect;
    private int cost;
    protected static Set<Object> globalOnDomain;

    public Movement() {
        this.cost = 1;
    }

    public boolean isApplicable(Map<Variable, Object> state) {
        for(Variable v : precondition.keySet()){
            if(!state.containsKey(v) || !precondition.get(v).equals(state.get(v))) return false;
        }
        return true;
    }

    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        Map<Variable, Object> newState = new HashMap<>();
        for(Variable v : state.keySet()){
            newState.put(v, state.get(v));
        }
        for(Variable v : effect.keySet()){
            newState.put(v, effect.get(v));
        }
        return newState;
    }

    public int getCost() {
        return this.cost;
    }

    public static void setGlobalOnDomain(int blocks, int stacks) {
        globalOnDomain = new HashSet<>();
        for(int i = -stacks; i<0; i++){
            globalOnDomain.add(i);
        }
        for(int i = 0; i < blocks; i++){
            globalOnDomain.add(i);
        }
    }
}
