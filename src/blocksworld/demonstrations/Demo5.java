package blocksworld.demonstrations;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import blocksworld.cp.heuristics.NbConstraintsVariableHeuristic;
import blocksworld.cp.heuristics.RandomValueHeuristic;
import blocksworld.cp.solvers.BacktrackSolver;
import blocksworld.cp.solvers.HeuristicMACSolver;
import blocksworld.cp.solvers.MACSolver;
import blocksworld.cp.solvers.Solver;
import blocksworld.modelling.constraints.Constraint;
import blocksworld.modelling.providers.ConstraintsProvider;
import blocksworld.modelling.providers.GeneralConstraintsProvider;
import blocksworld.modelling.providers.GrowingConstraintsProvider;
import blocksworld.modelling.providers.RegularityConstraintsProvider;
import blocksworld.modelling.providers.VariablesProvider;
import blocksworld.modelling.variables.Variable;

public class Demo5 {
    public static void main(String[] args) {
        int n, m;
        try {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
        } catch(Exception e) {
            System.out.println("paramétres invalides.\n\tparam 1 : nombre de blocs.\n\tparam 2 : nombre de piles.");
            return;
        }
        System.out.println("Demo 5 - Comparaison des temps de calcul des différents solveurs");

        System.out.println(String.format("\nla configuration souhaitée comporte %d blocs et %d piles.\nElle doit être régulière et croissante", n, m));

        // création des variables
        VariablesProvider variables_provider = new VariablesProvider(n, m);
        Set<Variable> variables = variables_provider.getVariables();

        // création des contraintes
        Set<Constraint> constraints = new HashSet<>();
        ConstraintsProvider general_constraints_provider = new GeneralConstraintsProvider(n, m);
        constraints.addAll(general_constraints_provider.getConstraints());
        ConstraintsProvider regularity_constraints_provider = new RegularityConstraintsProvider(n, m);
        constraints.addAll(regularity_constraints_provider.getConstraints());
        ConstraintsProvider growing_constraints_provider = new GrowingConstraintsProvider(n, m);
        constraints.addAll(growing_constraints_provider.getConstraints());

        benchMark(new BacktrackSolver(variables, constraints));
        benchMark(new MACSolver(variables, constraints));
        benchMark(new HeuristicMACSolver(variables, constraints, new NbConstraintsVariableHeuristic(constraints, true), new RandomValueHeuristic(new Random())));
    }

    private static void benchMark(Solver solver) {
        long timer = System.currentTimeMillis();
        solver.solve();
        System.out.println(String.format("\n- %s a trouvé une solution en %.2f secondes.", solver.getAlgo(), (float)(System.currentTimeMillis()-timer)/1000));
    }
}