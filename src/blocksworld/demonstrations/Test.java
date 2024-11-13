package blocksworld.demonstrations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import blocksworld.planning.planners.Soundable;
import blocksworld.planning.providers.MovementsProvider;

public class Test {
    public static void main(String[] args) {

        int n = 6, m = 3;
        
        
        Set<Object> allElementsDomain = new HashSet<>();
        for(int i = -m; i<0; i++){
            allElementsDomain.add(i);
        }
        for(int i = 0; i < n; i++){
            allElementsDomain.add(i);
        }
        
        System.out.println("- Configuration initiale : " + List.of(List.of(-1, 0, 1, 2), List.of(-2, 3), List.of(-3, 4)));
        System.out.println("- Configuration finale : " + List.of(List.of(-1), List.of(-2, 2, 1, 0, 4, 3), List.of(-3)) + "\n");

        // création de l'instanciation initiale
        Map<Variable, Object> start = createInstanciation(
            List.of(
                List.of(-1, 0, 2, 5),
                List.of(-2, 3),
                List.of(-3, 4, 1)
            ), n, m
        );

        // récupération des actions possibles
        MovementsProvider mp = new MovementsProvider(n, m);
        Set<Action> actions = mp.getActions();
        
        // création de l'instanciation finale et du but
        Map<Variable, Object> end = createInstanciation(
            List.of(
                List.of(-1, 2, 5),
                List.of(-2, 0, 3),
                List.of(-3, 4, 1)
            ), n, m
        );
        Goal goal = new BasicGoal(end);
                

        long timer;

        // Planner dfs = new DFSPlanner(start, actions, goal);
        // ((Soundable)dfs).activateNodeCount(true);
        // timer = System.currentTimeMillis();
        // List<Action> dfsPlan = dfs.plan();
        // System.out.println(String.format("* le planneur DFS a trouvé une solution\n\t> de %d étapes.\n\t> en explorant %d noeuds.\n\t> en %.3f secondes.\n", dfsPlan.size(), ((Soundable)dfs).getNodeCount(), (float)(System.currentTimeMillis()-timer)/1000));

        // Planner bfs = new BFSPlanner(start, actions, goal);
        // ((Soundable)bfs).activateNodeCount(true);
        // timer = System.currentTimeMillis();
        // List<Action> bfsPlan = bfs.plan();
        // System.out.println(String.format("* le planneur BFS a trouvé une solution\n\t> de %d étapes.\n\t> en explorant %d noeuds.\n\t> en %.3f secondes.\n", bfsPlan.size(), ((Soundable)bfs).getNodeCount(), (float)(System.currentTimeMillis()-timer)/1000));


        Planner dji = new DijkstraPlanner(start, actions, goal);
        ((Soundable)dji).activateNodeCount(true);
        timer = System.currentTimeMillis();
        List<Action> djiPlan = dji.plan();
        System.out.println(String.format("* le planneur Dijkstra a trouvé une solution\n\t> de %d étapes.\n\t> en explorant %d noeuds.\n\t> en %.3f secondes.\n", djiPlan.size(), ((Soundable)dji).getNodeCount(), (float)(System.currentTimeMillis()-timer)/1000));

        // Planner ast = new AStarPlanner(start, actions, goal, new MisplacedHeuristic(goal));
        // ((Soundable)ast).activateNodeCount(true);
        // timer = System.currentTimeMillis();
        // List<Action> astPlan = ast.plan();
        // System.out.println(String.format("* le planneur A* 1 a trouvé une solution\n\t> avec l'heuristique :\n\t\t- bloc mal placé (+1).\n\t> de %d étapes.\n\t> en explorant %d noeuds.\n\t> en %.3f secondes.\n", astPlan.size(), ((Soundable)ast).getNodeCount(), (float)(System.currentTimeMillis()-timer)/1000));

        // Planner ast2 = new AStarPlanner(start, actions, goal, new BlockedMisplacedHeuristic(goal));
        // ((Soundable)ast2).activateNodeCount(true);
        // timer = System.currentTimeMillis();
        // List<Action> ast2Plan = ast2.plan();
        // System.out.println(String.format("* le planneur A* 2 a trouvé une solution\n\t> avec l'heuristique :\n\t\t- bloc mal placé (+1).\n\t\t- bloc mal placé avec au moins un bloc au dessus (+2).\n\t> de %d étapes.\n\t> en explorant %d noeuds.\n\t> en %.3f secondes.\n", ast2Plan.size(), ((Soundable)ast2).getNodeCount(), (float)(System.currentTimeMillis()-timer)/1000));
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
}