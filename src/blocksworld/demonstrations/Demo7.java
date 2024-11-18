package blocksworld.demonstrations;

import java.util.List;
import java.util.Random;
import java.util.Set;

import blocksworld.datamining.database.AssociationRule;
import blocksworld.datamining.database.BooleanDatabase;
import blocksworld.datamining.miners.BruteForceAssociationRuleMiner;
import blocksworld.datamining.providers.BooleanVariablesProvider;
import blocksworld.modelling.variables.BooleanVariable;
import bwgeneratordemo.Demo;

public class Demo7 {
    public static void main(String[] args) {
        int n, m;
        float minFreq, minTrust;
        n = Demo.NB_BLOCKS;
        m = Demo.NB_STACKS;
        try {
            minFreq = Float.parseFloat(args[0]);
            minTrust = Float.parseFloat(args[1]);
        } catch(Exception e) {
            System.out.println("paramétres invalides.\n\tparam 1 : fréquence minimale.\n\tparam 2 : confiance minimale.");
            return;
        }
        System.out.println("Demo 7 - Extraction de connaissances\n");
        BooleanVariablesProvider provider = new BooleanVariablesProvider(n, m);
        BooleanDatabase db = new BooleanDatabase(provider.getBooleanVariables());
        for(int i=0; i<10000; i++) {
            List<List<Integer>> state = Demo.getState(new Random());
            Set<BooleanVariable> instance = provider.getInstance(state);
            db.add(instance);
        }

        BruteForceAssociationRuleMiner miner = new BruteForceAssociationRuleMiner(db);
        Set<AssociationRule> rules = miner.extract(minFreq, minTrust);
        System.out.println(String.format("Voici les régles d'association extraites d'une base de données de 10000 transactions pour la fréquence minimale %f et la confiance minimale %f :\n", minFreq, minTrust));
        for(AssociationRule rule : rules) System.out.println(rule);
    }
}
