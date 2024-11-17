package blocksworld.demonstrations;

import blocksworld.modelling.providers.GeneralConstraintsProvider;
import blocksworld.modelling.providers.GrowingConstraintsProvider;
import blocksworld.modelling.providers.RegularityConstraintsProvider;
import blocksworld.modelling.providers.VariablesProvider;

public class Demo1 {
    public static void main(String[] args) {
        int n, m;
        try {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
        } catch(Exception e) {
            System.out.println("paramétres invalides.\n\tparam 1 : nombre de blocs.\n\tparam 2 : nombre de piles.");
            return;
        }

        System.out.println("Demo 1 - Génération des variables et des contraintes");
        VariablesProvider v = new VariablesProvider(n, m);
        GeneralConstraintsProvider gc = new GeneralConstraintsProvider(n,m);
        RegularityConstraintsProvider rc = new RegularityConstraintsProvider(n, m);
        GrowingConstraintsProvider cc = new GrowingConstraintsProvider(n, m);
        System.out.println(String.format("\npour un monde des blocks avec %d blocks et %d piles, on aura :", n, m));
        System.out.println(String.format("- %d variables (%d On, %d Fixed, %d Free).", v.getVariables().size(), v.getOnVariables().size(), v.getFixedVariables().size(), v.getFreeVariables().size()));
        System.out.println(String.format("- %d contraintes générales.", gc.getConstraints().size()));
        System.out.println(String.format("- %d contraintes de régularité.", rc.getConstraints().size()));
        System.out.println(String.format("- %d contraintes de croissance.", cc.getConstraints().size()));
    }
}