package blocksworld.datamining.miners;

import java.util.Set;

import blocksworld.datamining.database.AssociationRule;
import blocksworld.datamining.database.BooleanDatabase;

// Interface représentant un extracteur de régles d'associations.
public interface AssociationRuleMiner {
    public BooleanDatabase getDatabase();
    // Retourne l'ensemble des régles d'association de fréquence >= minFrequency et de confiance >= minTrust.
    public Set<AssociationRule> extract(float minFrequency, float minTrust);
}
