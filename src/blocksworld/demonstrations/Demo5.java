package blocksworld.demonstrations;

import java.util.HashSet;
import java.util.Set;

import blocksworld.cp.solvers.BacktrackSolver;
import blocksworld.cp.solvers.MACSolver;
import blocksworld.cp.solvers.Solver;
import blocksworld.modelling.constraints.Constraint;
import blocksworld.modelling.constraints.NonOverlappingConstraint;
import blocksworld.modelling.providers.ConstraintsProvider;
import blocksworld.modelling.providers.GeneralConstraintsProvider;
import blocksworld.modelling.providers.RegularityConstraintsProvider;
import blocksworld.modelling.providers.VariablesProvider;
import blocksworld.modelling.variables.Variable;

public class Demo5 {
    public static void main(String[] args) {
        System.out.println("demo5");
        int n = 5, m = 5;
        VariablesProvider variables_provider = new VariablesProvider(n, m);
        ConstraintsProvider general_constraints_provider = new GeneralConstraintsProvider(n, m);
        ConstraintsProvider regularity_constraints_provider = new RegularityConstraintsProvider(n, m);

        Set<Variable> variables = variables_provider.getVariables();
        Set<Constraint> constraints = new HashSet<>();
        constraints.addAll(general_constraints_provider.getConstraints());

        for(Constraint c : constraints) {
            if(c instanceof NonOverlappingConstraint) System.out.println(c);
        }
        // constraints.addAll(regularity_constraints_provider.getConstraints());

        Solver solver = new MACSolver(variables, constraints);
        System.out.println(solver.solve());
    }
}
