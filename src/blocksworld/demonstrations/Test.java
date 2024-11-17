package blocksworld.demonstrations;

import java.util.List;
import java.util.Random;
import java.util.Set;

import blocksworld.datamining.database.AssociationRule;
import blocksworld.datamining.database.BooleanDatabase;
import blocksworld.datamining.miners.BruteForceAssociationRuleMiner;
import blocksworld.datamining.providers.BooleanVariablesProvider;
import blocksworld.modelling.variables.BooleanVariable;
import bwgenerator.BWGenerator;

public class Test {
    public static void main(String[] args) {
        System.out.println("Demo 6 - Extraction de connaissances");
        int n, m;
        n = 10;
        m = 5;
        BooleanVariablesProvider provider = new BooleanVariablesProvider(n, m);
        BWGenerator generator = new BWGenerator(n, m);
        BooleanDatabase db = new BooleanDatabase(provider.getBooleanVariables());
        List<List<Integer>> state = generator.generate(new Random());
        Set<BooleanVariable> instance = provider.getInstance(state);
        System.out.println(state);
        System.out.println(instance);
    }
}
