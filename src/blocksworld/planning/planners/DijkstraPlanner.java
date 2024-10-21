package blocksworld.planning.planners;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

import blocksworld.modelling.variables.Variable;
import blocksworld.planning.actions.Action;
import blocksworld.planning.goals.Goal;

import java.util.Queue;
import java.util.LinkedList;

public class DijkstraPlanner implements Planner, Soundable{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private boolean isNodeCountActive = false;
    private int nodeCount = 0;


    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public List<Action> plan(){
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Set<Map<Variable, Object>> goals = new HashSet<>();
        father.put(initialState, null);
        distance.put(initialState, 0f);
        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(initialState);
        while(!open.isEmpty()){
            Map<Variable, Object> instantiation = choose(open, distance);
            open.remove(instantiation);
            if(isNodeCountActive) nodeCount++; // sonde de noeuds explorés
            if(goal.isSatisfiedBy(instantiation)) goals.add(instantiation);
            for(Action action : actions){
                if(action.isApplicable(instantiation)){
                    Map<Variable, Object> next = action.successor(instantiation);
                    if(!distance.keySet().contains(next)) distance.put(next, Float.POSITIVE_INFINITY);
                    if(distance.get(next) > distance.get(instantiation) + action.getCost()){
                        distance.put(next, distance.get(instantiation) + action.getCost());
                        father.put(next, instantiation);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        if(goals.isEmpty()) return null;
        return getDijkstraPlan(father, plan, goals, distance);
    }

    private Map<Variable, Object> choose(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> distances){
        Map<Variable, Object> argmin = (open.iterator(  ).hasNext() ? open.iterator().next() : null);
        for(Map<Variable, Object> instanciation : open){
            argmin = (distances.get(instanciation) < distances.get(argmin) ? instanciation : argmin);
        }
        return argmin;
    }

    private List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Float> distance){
        Queue<Action> dijPlan = new LinkedList<>();
        Map<Variable, Object> goal = choose(goals, distance);
        while(father.get(goal) != null){
            dijPlan.add(plan.get(goal));
            goal = father.get(goal);
        }
        Stack<Action> inversionStack = new Stack<>();
        while(!dijPlan.isEmpty()){
            inversionStack.push(dijPlan.remove());
        }
        while(!inversionStack.isEmpty()){
            dijPlan.add(inversionStack.pop());
        }
        return new ArrayList<>(dijPlan);
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
        return "Dijkstra";
    }
}