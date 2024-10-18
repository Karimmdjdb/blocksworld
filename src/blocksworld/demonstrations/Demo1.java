package blocksworld.demonstrations;

import blocksworld.modelling.providers.GeneralConstraintsProvider;
import blocksworld.modelling.providers.GrowingConstraintsProvider;
import blocksworld.modelling.providers.RegularityConstraintsProvider;
import blocksworld.modelling.providers.VariablesProvider;

public class Demo1 {
    public static void main(String[] args) {
        int n=Integer.parseInt(args[0]), m=Integer.parseInt(args[1]);
        VariablesProvider v = new VariablesProvider(n, m);
        GeneralConstraintsProvider gc = new GeneralConstraintsProvider(n,m);
        RegularityConstraintsProvider rc = new RegularityConstraintsProvider(n, m);
        GrowingConstraintsProvider cc = new GrowingConstraintsProvider(n, m);
        System.out.println(String.format("pour un monde des blocks avec %d blocks et %d piles, on aura :", n, m));
        System.out.println(String.format("- %d variables.", v.getVariables().size()));
        System.out.println(String.format("- %d contraintes générales.", gc.getConstraints().size()));
        System.out.println(String.format("- %d contraintes de régularité.", rc.getConstraints().size()));
        System.out.println(String.format("- %d contraintes de croisement.", cc.getConstraints().size()));
    }
}