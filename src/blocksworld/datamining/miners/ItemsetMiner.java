package blocksworld.datamining.miners;

import java.util.Set;

import blocksworld.datamining.database.BooleanDatabase;
import blocksworld.datamining.database.Itemset;

public interface ItemsetMiner {
    public BooleanDatabase getDatabase();
    // Retourne l'ensemble des Itemsets ayant une fréquence >= minFrequency.
    public Set<Itemset> extract(float minFrequency);
}
