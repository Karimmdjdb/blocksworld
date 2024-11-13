package blocksworld.datamining.miners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blocksworld.datamining.database.AssociationRule;
import blocksworld.datamining.database.BooleanDatabase;
import blocksworld.datamining.database.Itemset;
import blocksworld.modelling.variables.BooleanVariable;

// Classe représentant un exctracteur de régles d'associations qui se base sur l'algorithme de brute force.
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    public BruteForceAssociationRuleMiner(BooleanDatabase db) {
        super(db);
    }

    // on se base sur un algorithme récursif qui nous permet de construire un arbre binaire,
    // dans lequel chaque chemin correspond à une partie de l'ensemble initial,
    // chaque étage correspond à un élément de l'ensemble, le sous arbre gauche corresppnd à l'absence,
    // de cet élément tandis que le sous arbre droit correspond à la présence de.
    // ce dernier
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        int nbVars = items.size();
        List<BooleanVariable> itemsList = new ArrayList<>(items);
        Set<Set<BooleanVariable>> result = new HashSet<>();
        buildPowersetTree(result, new ArrayList<>(), nbVars, itemsList);
        result.remove(Set.of());
        result.remove(items);
        return result;
    }

    private static void buildPowersetTree(Set<Set<BooleanVariable>> result, List<Boolean> present,  int n, List<BooleanVariable> itemsList) {
        if(n <= 0) {
            Set<BooleanVariable> toAdd = new HashSet<>();
            for(int i = 0; i < itemsList.size(); i++) {
                if(present.get(i).equals(true)) toAdd.add(itemsList.get(i));
            }
            result.add(toAdd);
            return;
        }
        List<Boolean> copy1 = new ArrayList<>(present);
        copy1.add(false);
        buildPowersetTree(result, copy1, n-1, itemsList);
        List<Boolean> copy2 = new ArrayList<>(present);
        copy2.add(true);
        buildPowersetTree(result, copy2, n-1, itemsList);
    }

    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> extracted = new HashSet<>();

        // On récupére les itemsets fréquents de la base de données.
        Set<Itemset> frequent = new Apriori(getDatabase()).extract(minFrequency);

        for(Itemset z : frequent) { // Pour chanque itemset.
            // On calcule les sous ensembles de cet ensemble (les candidats pour créer une régle d'association).
            Set<Set<BooleanVariable>> candidates = allCandidatePremises(z.getItems());
            // on associe ces candidats deux par deux et on vérifie si la régle issue vérifie les conditions.
            for(Set<BooleanVariable> x : candidates) {
                Set<BooleanVariable> y = new HashSet<>(z.getItems());
                y.removeAll(x);
                Set<BooleanVariable> union = new HashSet<>(x), intersection = new HashSet<>(x);
                union.addAll(y);
                intersection.retainAll(y);
                if(union.equals(z.getItems()) && intersection.isEmpty()) {
                    float confidence = confidence(x, y, frequent);
                    if(confidence >= minConfidence) {
                        float frequency = frequency(union, frequent);
                        extracted.add(new AssociationRule(x, y, frequency, confidence));
                    }
                }
            }
        }

        return extracted;
    }
}
