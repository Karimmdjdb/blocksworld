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
import blocksworld.planning.heuristics.Heuristic;

public class AStarPlanner implements Planner, Soundable{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;
    private boolean isNodeCountActive = false;
    private int nodeCount = 0;


    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
    }

    public List<Action> plan(){
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(initialState);
        father.put(initialState, null);
        distance.put(initialState, 0f);
        value.put(initialState, heuristic.estimate(initialState));
        while(!open.isEmpty()){
            Map<Variable, Object> instantiation = choose(open, value);
            if(isNodeCountActive) nodeCount++; // sonde de noeuds explorés
            if(goal.isSatisfiedBy(instantiation)) return getBfsPlan(father, plan, instantiation);
            else{
                open.remove(instantiation);
                for(Action action : actions){
                    if(action.isApplicable(instantiation)){
                        Map<Variable, Object> next = action.successor(instantiation);
                        if(!distance.containsKey(next)) distance.put(next, Float.POSITIVE_INFINITY);
                        if(distance.get(next) > distance.get(instantiation) + action.getCost()){
                            distance.put(next, distance.get(instantiation) + action.getCost());
                            value.put(next, distance.get(next) + heuristic.estimate(next));
                            father.put(next, instantiation);
                            plan.put(next, action);
                            open.add(next);
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

    private Map<Variable, Object> choose(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> values){
        Map<Variable, Object> argmin = (open.iterator(  ).hasNext() ? open.iterator().next() : null);
        for(Map<Variable, Object> instanciation : open){
            argmin = (values.get(instanciation) < values.get(argmin) ? instanciation : argmin);
        }
        return argmin;
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
        return "A*";
    }
}