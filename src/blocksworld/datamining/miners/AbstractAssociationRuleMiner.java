package blocksworld.datamining.miners;

import java.util.HashSet;
import java.util.Set;

import blocksworld.datamining.database.BooleanDatabase;
import blocksworld.datamining.database.Itemset;
import blocksworld.modelling.variables.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    private BooleanDatabase db;

    public AbstractAssociationRuleMiner(BooleanDatabase db) {
        this.db = db;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return db;
    }

    // Retourne la fréquence du set d'items "items" telle qu'elle est stockée dans "itemsets".
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for(Itemset itemset : itemsets) {
            if(itemset.getItems().equals(items)) return itemset.getFrequency();
        }
        throw new IllegalArgumentException();
    }

    // Retourne la confiance d'une régle d'association R = (X : premise -> Y : conclusion) avec la formule conf(R) = freq(XY) / freq(X).
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        Set<BooleanVariable> union = new HashSet<>();
        union.addAll(premise);
        union.addAll(conclusion);

        return frequency(union, itemsets) / frequency(premise, itemsets);
    }
}
