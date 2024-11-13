package blocksworld.datamining.database;

import java.util.Set;

import blocksworld.modelling.variables.BooleanVariable;

// Classe représentant un ensemble d'items avec la fréquence d'apparition de l'ensemble danss la base de données.
public class Itemset {

    private Set<BooleanVariable> items;
    private float frequency;

    public Itemset(Set<BooleanVariable> items, float frequency) {
        this.items = items;
        this.frequency = frequency;
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public float getFrequency() {
        return frequency;
    }

    public String toString() {
        return items.toString() + "<" + frequency + ">";
    }

    @Override // nous permet d'éviter la redondance dans les collections.
    public boolean equals(Object other) {
        if(!(other instanceof Itemset)) return false;
        Itemset otherItemset = (Itemset)other;
        return this.items.equals(otherItemset.items);
    }

    @Override // kifkif.
    public int hashCode() {
        return items.hashCode();
    }
}
