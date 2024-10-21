package blocksworld.demonstrations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.lang.IllegalArgumentException;

import javax.swing.JFrame;

import blocksworld.modelling.variables.FixedVariable;
import blocksworld.modelling.variables.FreeVariable;
import blocksworld.modelling.variables.OnVariable;
import blocksworld.modelling.variables.Variable;
import blocksworld.planning.actions.Action;
import blocksworld.planning.goals.BasicGoal;
import blocksworld.planning.goals.Goal;
import blocksworld.planning.heuristics.BlockedMisplacedHeuristic;
import blocksworld.planning.heuristics.MisplacedHeuristic;
import blocksworld.planning.planners.AStarPlanner;
import blocksworld.planning.planners.BFSPlanner;
import blocksworld.planning.planners.DFSPlanner;
import blocksworld.planning.planners.DijkstraPlanner;
import blocksworld.planning.planners.Planner;
import blocksworld.planning.providers.MovementsProvider;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;

public class Demo4 {
    public static void main(String[] args) {

        int n = 5, m = 3;
        int mode;
        try {
            mode = Integer.parseInt(args[0]);
            if(mode < 1 || mode > 4) throw new IllegalArgumentException();
        } catch(Exception e) {
            System.out.println("paramétres invalides.\nil faut spécifier un paramétre mode qui peut prendre les valeurs entre 1 et 4\n\t- 1 : DFS.\n\t- 2 : BFS.\n\t- 3 : Dijkstra.\n\t- 4 : A*.");
            return;
        }
        
        Set<Object> allElementsDomain = new HashSet<>();
        for(int i = -m; i<0; i++){
            allElementsDomain.add(i);
        }
        for(int i = 0; i < n; i++){
            allElementsDomain.add(i);
        }

        // création de l'instanciation initiale
        Map<Variable, Object> start = createInstanciation(
            List.of(
                List.of(-1, 0, 1, 2),
                List.of(-2, 3),
                List.of(-3, 4)
                ), n, m
        );

        // récupération des actions possibles
        MovementsProvider mp = new MovementsProvider(n, m);
        Set<Action> actions = mp.getActions();
        
        // création de l'instanciation finale et du but
        Map<Variable, Object> end = createInstanciation(
            List.of(
                List.of(-1),
                List.of(-2, 2, 1, 0, 4, 3),
                List.of(-3)
            ), n, m
        );
        Goal goal = new BasicGoal(end);
                

        switch (mode) {
            case 1:
                resolveInGUI(new DFSPlanner(start, actions, goal), n);
                break;
            case 2:
                resolveInGUI(new BFSPlanner(start, actions, goal), n);
                break;
            case 3:
                resolveInGUI(new DijkstraPlanner(start, actions, goal), n);
                break;
            case 4:
                resolveInGUI(new AStarPlanner(start, actions, goal, new BlockedMisplacedHeuristic(goal)), n);        
                break;
            default:
                break;
        }
    }

    private static Map<Variable, Object> createInstanciation(List<List<Integer>> stacks, int blocsCount, int stacksCount) {
        // création d'un domaine global pour pouvoir instancier les variables de type On.
        Set<Object> allElementsDomain = new HashSet<>();
        for(int i = -stacksCount; i<0; i++){
            allElementsDomain.add(i);
        }
        for(int i = 0; i < blocsCount; i++){
            allElementsDomain.add(i);
        }

        // remplissage de la Map
        Map<Variable, Object> instanciation = new HashMap<>();
        for(List<Integer> stack : stacks) {
            int size = stack.size();
            for(int i = 0; i < size; i++) {
                boolean blocked = (i == size - 1 ? false : true); // bloqué s'il n'est pas le dernier élément de la pile
                int id = stack.get(i);
                if(id >= 0){ // si c'est un bloc
                    int underId = stack.get(i-1);
                    instanciation.put(new OnVariable(id, allElementsDomain), underId);
                    instanciation.put(new FixedVariable(id), blocked);
                } else { // si c'est une pile
                    instanciation.put(new FreeVariable(id), !blocked);
                }
            }
        }
        return instanciation;
    }

    private static BWState<Integer> makeBWState(Map<Variable, Object> instanciation, int n) {
        // Building state
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Variable onB = null; // get instance of Variable for "on_b"
            for(Variable var : instanciation.keySet()) {
                if(var.getId() == b && (var instanceof OnVariable)) onB = var;
            }
            int under = (int) instanciation.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        return builder.getState();
    }

    private static void resolveInGUI(Planner planner, int n) {
        List<Action> plan = planner.plan();

        Map<Variable, Object> state = planner.getInitialState();
        BWIntegerGUI gui = new BWIntegerGUI(n);
        JFrame frame = new JFrame(planner.getAlgo());
        BWState<Integer> bwState = makeBWState(state, n);
        BWComponent<Integer> component = gui.getComponent(bwState);
        frame.add(component);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Playing plan
        for (Action a: plan) {
            try { Thread.sleep(1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            state=a.successor((Map<Variable, Object>)state);
            component.setState(makeBWState(state, n));
        }
        System.out.println("Simulation of plan: done.");
    }
}

