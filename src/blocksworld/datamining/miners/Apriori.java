package blocksworld.datamining.miners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import blocksworld.datamining.database.BooleanDatabase;
import blocksworld.datamining.database.Itemset;
import blocksworld.modelling.variables.BooleanVariable;

// Classe qui représente les extracteurs d'Itemsets fonctionnant sur le principe de l'algorithme "apriori".
public class Apriori extends AbstractItemsetMiner {

    public Apriori(BooleanDatabase db) {
        super(db);
    }

    // Retourne l'ensemble de tous les itemsets singletons (qui possédent un seul item) dont la fréquence >= minFrequency.
    public Set<Itemset> frequentSingletons(float minFrequency) {
        Set<Itemset> singletons = new HashSet<>();
        for(BooleanVariable item : getDatabase().getItems()) {
            Set<BooleanVariable> pattern = Set.of(item);
            float freq = frequency(pattern);
            if(freq >= minFrequency) singletons.add(new Itemset(pattern, freq));
        }
        return singletons;
    }

    // Retourne un ensemble trié issu de la combinaison de deux ensembles triés d'items.
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> s1, SortedSet<BooleanVariable> s2) {
        if(s1.isEmpty() || s1.size() != s2.size()) return null; // on vérifie si les deux ensembles ont la même taille.
        if(!s1.subSet(s1.first(), s1.last()).equals(s2.subSet(s2.first(), s2.last()))) return null; // on compare s1[1..k-1] et s2[1..k-1] (car subSet exclut le toElement).
        if(s1.last().equals(s2.last())) return null; // on compare s1[k] et s2[k].
        SortedSet<BooleanVariable> sorted = new TreeSet<>(COMPARATOR);
        sorted.addAll(s1);
        sorted.addAll(s2);
        return sorted;
    }

    // En considérant que items est de taille k,
    // Retourne true si tous les sous ensembles d'items de taille k-1 sont contenus dans frequents, false sinon.
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequents) {
        for(BooleanVariable item : items) {
            Set<BooleanVariable> newItems = new HashSet<>(items);
            newItems.remove(item);
            if(!frequents.contains(newItems)) return false;
        }
        return true;
    }

    // Retourne l'ensemble de tous les Itemsets fréquents dans les transactions de la base de données,
    @Override
    public Set<Itemset> extract(float minFrequency) {
        int level = 1;
        Set<Itemset> extracted = new HashSet<>(); // itemsets extraits.
        Set<Itemset> addToExtracted = new HashSet<>(); // pour éviter une concurrence de modification.
        List<SortedSet<BooleanVariable>> frequents = new ArrayList<>(); // les itemsets fréquents extraits au dernier niveau.

        while(level <= getDatabase().getItems().size()) {
            List<SortedSet<BooleanVariable>> newFrequents = new ArrayList<>(); // stock les fréquents qu'on découvrira au niveau courant.

            // si on n'a rien extrait, on extrait les singletons.
            if(extracted.isEmpty()) {
                extracted.addAll(frequentSingletons(minFrequency));
                for(Itemset itemset : frequentSingletons(minFrequency)) {
                    SortedSet<BooleanVariable> s = new TreeSet<>(COMPARATOR);
                    s.addAll(itemset.getItems());
                    newFrequents.add(s);
                }
            }

            // sinon on combine les itemsets déja extraits deux par deux.
            for(Itemset itemset1 : extracted)
            for(Itemset itemset2 : extracted) {
                if(itemset1.getItems().equals(itemset2.getItems())) continue; // on ne combine pas un itemset avec lui même.

                // création de SortedSets à partir des itemsets à combiner.
                SortedSet<BooleanVariable> s1 = new TreeSet<>(COMPARATOR), s2 = new TreeSet<>(COMPARATOR);
                s1.addAll(itemset1.getItems());
                s2.addAll(itemset2.getItems());

                // combinaison des deux SortedSets.
                SortedSet<BooleanVariable> combined = combine(s1, s2);


                if(combined != null && frequency(combined) >= minFrequency && allSubsetsFrequent(combined, frequents)) {
                    addToExtracted.add(new Itemset(combined, frequency(combined)));
                    newFrequents.add(combined);
                }
            }

            extracted.addAll(addToExtracted);
            addToExtracted.clear();
            frequents = newFrequents;
            level++;
        }
        return extracted;
    }

}
