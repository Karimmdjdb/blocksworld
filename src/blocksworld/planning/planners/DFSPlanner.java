package blocksworld.planning.planners;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

import blocksworld.modelling.variables.Variable;
import blocksworld.planning.actions.Action;
import blocksworld.planning.goals.Goal;

public class DFSPlanner implements Planner, Soundable{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private boolean isNodeCountActive = false;
    private int nodeCount = 0;


    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public List<Action> plan(){
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(initialState);
        return dfs(new HashMap<>(initialState), new Stack<>(), closed);
    }

    private List<Action> dfs(Map<Variable, Object> instanciation, Stack<Action> plan, Set<Map<Variable, Object>> closed) {
        if(instanciation == null) return null;
        if(goal.isSatisfiedBy(instanciation)) return plan;
        else{
            for(Action action : actions){
                if(action.isApplicable(instanciation)){
                    Map<Variable, Object> next = action.successor(instanciation);
                    if(!closed.contains(next)){
                        plan.push(action);
                        closed.add(next);
                        if(isNodeCountActive) nodeCount++; // sonde de noeuds explorés
                        List<Action> subplan = this.dfs(next, plan, closed);
                        if(subplan != null) return subplan;
                        else plan.pop();
                    }
                }
            }
            return null;
        }
    }

    public Map<Variable, Object> getInitialState(){
        return initialState;
    }

    public Set<Action> getActions(){
        return actions;
    }

    public Goal getGoal(){
        return goal;
    }

    public void activateNodeCount(boolean active){
        isNodeCountActive = active;
    }

    public int getNodeCount(){
        return nodeCount;
    }

    @Override
    public String getAlgo() {
        return "DFS";
    }
}