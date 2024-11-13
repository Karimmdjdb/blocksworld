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
        Itemset itemset = new Itemset(items, 0f); // on peut donner 0 en fréquence car cet itemset nous servira juste a retrouver son équivalent dans le set.
        if(!itemsets.contains(itemset)) throw new IllegalArgumentException();
        for(Itemset itemset2 : itemsets) {
            if(itemset2.equals(itemset)) return itemset2.getFrequency();
        }
        return 0f;
    }

    // Retourne la confiance d'une régle d'association R = (X : premise -> Y : conclusion) avec la formule conf(R) = freq(XY) / freq(X).
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        Set<BooleanVariable> union = new HashSet<>();
        union.addAll(premise);
        union.addAll(conclusion);

        Itemset premiseSet = new Itemset(premise, 0f); // on peut donner 0 en fréquence car cet itemset nous servira juste a retrouver son équivalent dans le set.
        Itemset unionSet = new Itemset(union, 0f); // idem/
        for(Itemset itemsset : itemsets) {
            if(itemsset.equals(premiseSet)) premiseSet = itemsset;
            if(itemsset.equals(unionSet)) unionSet = itemsset;
        }
        return unionSet.getFrequency() / premiseSet.getFrequency();
    }
}
