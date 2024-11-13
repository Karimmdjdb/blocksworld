package blocksworld.datamining.miners;

import java.util.Comparator;
import java.util.Set;

import blocksworld.datamining.database.BooleanDatabase;
import blocksworld.modelling.variables.BooleanVariable;

public abstract class AbstractItemsetMiner implements ItemsetMiner {
    // Comparateur que vont utiliser les SortedSets pour ordonner leurs valeurs.
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> ((Integer)var1.getId()).compareTo(var2.getId());
    private BooleanDatabase db;

    public AbstractItemsetMiner(BooleanDatabase db) {
        this.db = db;
    }

    // Retourne la fréquence de l'ensemble itemset dans la base de données.
    public float frequency(Set<BooleanVariable> itemset) {
        double frequency = 0f, fraction = 1d / db.getTransactions().size();
        for(Set<BooleanVariable> transaction : db.getTransactions()) {
            if(transaction.containsAll(itemset))  frequency += fraction;
        }
        return (float)frequency;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return db;
    }
}
