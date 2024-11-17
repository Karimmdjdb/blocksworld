package blocksworld.demonstrations;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

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
import blocksworld.modelling.variables.OnVariable;
import blocksworld.modelling.variables.Variable;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;

public class Demo6 {
    public static void main(String[] args) {
        int n, m, mode, mode2;
        try {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            mode = Integer.parseInt(args[2]);
            mode2 = Integer.parseInt(args[3]);
            if(mode < 1 || mode > 3 || mode2 < 1 || mode2 > 3) throw new IllegalArgumentException();
        } catch(Exception e) {
            System.out.println("paramétres invalides.\n\tparam 1 : nombre de blocs.\n\tparam 2 : nombre de piles.\n\tparam 3 : configuration souhaitée (1 : régulière, 2 : croissante, 3 : les deux).\n\tparam 4 : solveur (1 : Backtrack, 2 : MAC, 3 : MAC avec heuristiques).");
            return;
        }
        System.out.println("Demo 6 - Visualisation de la solution d'un CSP");

        // création des variables
        VariablesProvider variables_provider = new VariablesProvider(n, m);
        Set<Variable> variables = variables_provider.getVariables();

        // création des contraintes
        Set<Constraint> constraints = new HashSet<>();
        ConstraintsProvider general_constraints_provider = new GeneralConstraintsProvider(n, m);
        constraints.addAll(general_constraints_provider.getConstraints());
        System.out.println("\n- La configuration souhaitée est :");
        System.out.println(String.format("\t* %d bloc(s) et %d pile(s).", n, m));
        // dans le cas d'un monde régulier ou les deux
        if(mode == 1 || mode == 3) {
            System.out.println("\t* régulière.");
            ConstraintsProvider regularity_constraints_provider = new RegularityConstraintsProvider(n, m);
            constraints.addAll(regularity_constraints_provider.getConstraints());
        }
        // dans le cas d'un monde croissant ou les deux
        if(mode == 2 || mode == 3) {
            System.out.println("\t* croissante.");
            ConstraintsProvider growing_constraints_provider = new GrowingConstraintsProvider(n, m);
            constraints.addAll(growing_constraints_provider.getConstraints());
        }


        Solver solver;
        System.out.println("\n- L'algorithme utilisé par le solveur est :");
        switch(mode2) {
            case 1: // Backtrack solver
            System.out.println("\t* backtracking.");
            solver = new BacktrackSolver(variables, constraints);
            break;
            case 2: // MAC solver
            System.out.println("\t* maintaining arc consitency.");
                solver = new MACSolver(variables, constraints);
                break;
            case 3: // Heuristic MAC solver
                System.out.println("\t* maintaining arc consitency (avec heuristiques).");
                solver = new HeuristicMACSolver(variables, constraints, new NbConstraintsVariableHeuristic(constraints, true), new RandomValueHeuristic(new Random()));
                break;
            default:
                return;
        }
        Map<Variable, Object> instanciation = solver.solve();
        if(instanciation == null) {
            System.out.println("Aucune solution n'a été trouvée.");
            return;
        }

        System.out.println("\nUne solution a été trouvée.");

        // Building state
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Variable onB = null; // get instance of Variable for "on_b"
            for(Variable var : instanciation.keySet()) {
                if(var.getId() == b && var instanceof OnVariable) onB = var;
            }
            int under = (int) instanciation.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        BWState<Integer> state = builder.getState();
        // Displaying
        BWIntegerGUI gui = new BWIntegerGUI(n);
        JFrame frame = new JFrame(solver.getAlgo());
        frame.add(gui.getComponent(state));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
