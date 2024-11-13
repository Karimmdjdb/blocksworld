package blocksworld.planning.planners;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

import blocksworld.modelling.variables.Variable;
import blocksworld.planning.actions.Action;
import blocksworld.planning.goals.Goal;

public class BFSPlanner implements Planner, Soundable{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private boolean isNodeCountActive = false;
    private int nodeCount = 0;


    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public List<Action> plan(){
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(initialState);
        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(initialState);
        father.put(initialState, null);
        if(goal.isSatisfiedBy(initialState)) return new ArrayList<>();
        while(!open.isEmpty()){
            Map<Variable, Object> instantiation = open.remove();
            closed.add(instantiation);
            for(Action action : actions){
                if(action.isApplicable(instantiation))
                {
                    Map<Variable, Object> next = action.successor(instantiation);
                    if(!closed.contains(next) && !open.contains(next)){
                        father.put(next, instantiation);
                        plan.put(next, action);
                        if(goal.isSatisfiedBy(next)) return getBfsPlan(father, plan, next);
                        else{
                            open.add(next);
                            if(isNodeCountActive) nodeCount++; // sonde de noeuds explorés
                        }
                    }
                }
            }
        }
        return null;
    }

    private List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal){
        Queue<Action> bfsPlan = new LinkedList<>();
        while(goal != null && !goal.isEmpty()){
            if(plan.get(goal) != null) bfsPlan.add(plan.get(goal));
            goal = father.get(goal);
        }
        Stack<Action> inversionStack = new Stack<>();
        while(!bfsPlan.isEmpty()){
            inversionStack.push(bfsPlan.remove());
        }
        while(!inversionStack.isEmpty()){
            bfsPlan.add(inversionStack.pop());
        }
        return new ArrayList<>(bfsPlan);
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
        return "BFS";
    }
}