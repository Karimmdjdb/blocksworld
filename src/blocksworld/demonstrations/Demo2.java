package blocksworld.demonstrations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.constraints.Constraint;
import blocksworld.modelling.providers.GrowingConstraintsProvider;
import blocksworld.modelling.providers.RegularityConstraintsProvider;
import blocksworld.modelling.variables.OnVariable;
import blocksworld.modelling.variables.Variable;

public class Demo2 {
    public static void main(String[] args) {
        System.out.println("Demo 2 - Exemples de configurations");

        int n = 10, m = 5;
        RegularityConstraintsProvider rc = new RegularityConstraintsProvider(n, m);
        GrowingConstraintsProvider cc = new GrowingConstraintsProvider(n, m);

        Set<Object> allElementsDomain = new HashSet<>();
        for(int i = -m; i<0; i++){
            allElementsDomain.add(i);
        }
        for(int i = 0; i < n; i++){
            allElementsDomain.add(i);
        }

        Set<Map<Variable, Object>> instanciations = Set.of(
            Map.of(
                new OnVariable(6, allElementsDomain), 4,
                new OnVariable(4, allElementsDomain), 2,
                new OnVariable(2, allElementsDomain), 0,
                new OnVariable(0, allElementsDomain), -1,
                new OnVariable(9, allElementsDomain), 5,
                new OnVariable(5, allElementsDomain), 1,
                new OnVariable(1, allElementsDomain), -2,
                new OnVariable(8, allElementsDomain), 7,
                new OnVariable(7, allElementsDomain), -3,
                new OnVariable(3, allElementsDomain), -4
            ),
            Map.of(
                new OnVariable(6, allElementsDomain), -5,
                new OnVariable(4, allElementsDomain), 0,
                new OnVariable(2, allElementsDomain), 9,
                new OnVariable(0, allElementsDomain), -1,
                new OnVariable(9, allElementsDomain), -4,
                new OnVariable(5, allElementsDomain), 7,
                new OnVariable(1, allElementsDomain), 3,
                new OnVariable(8, allElementsDomain), 4,
                new OnVariable(7, allElementsDomain), -3,
                new OnVariable(3, allElementsDomain), 5
            ),
            Map.of(
                new OnVariable(6, allElementsDomain), 4,
                new OnVariable(4, allElementsDomain), -2,
                new OnVariable(2, allElementsDomain), 1,
                new OnVariable(0, allElementsDomain), -1,
                new OnVariable(9, allElementsDomain), 5,
                new OnVariable(5, allElementsDomain), -3,
                new OnVariable(1, allElementsDomain), 0,
                new OnVariable(8, allElementsDomain), -5,
                new OnVariable(7, allElementsDomain), 6,
                new OnVariable(3, allElementsDomain), 2
            )
        );
        int i = 1;
        for(Map<Variable, Object> instanciation : instanciations) {
            System.out.println(String.format("\nExemple %d ---------------------------------", i));
            System.out.println("- la configuration : " + instanciation);
            boolean reg = true;
            boolean gro = true;
            for(Constraint c : rc.getConstraints()) {
                if(!c.isSatisfiedBy(instanciation)) reg = false;
            }
            for(Constraint c : cc.getConstraints()) {
                if(!c.isSatisfiedBy(instanciation)) gro = false;
            }
            System.out.println("\t* " + (reg ? "est réguliére" : "n'est pas réguliére"));
            System.out.println("\t* " + (gro ? "est croisante" : "n'est pas croissante"));
            i++;
        }
    }
}