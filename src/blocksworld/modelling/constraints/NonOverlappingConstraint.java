package blocksworld.modelling.constraints;

import java.util.Map;

import blocksworld.modelling.variables.Variable;
import util.ClassComparator;


// Classe qui représente la contrainte On(b) ≠ On(b')
public class NonOverlappingConstraint extends BinaryConstraint{
    public NonOverlappingConstraint(Variable v1, Variable v2) {
        super(v1, v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        checkIfScopeIsTreated(instanciation);
        return !instanciation.get(v1).equals(instanciation.get(v2));
    }

    // on redéfinit la méthode équals car ici l'ordre des variables ne compte pas, non-overlapping(v1, v2) est équivalent à non-overlapping(v2, v1)
    @Override
    public boolean equals(Object other) {
        if(ClassComparator.hardComparaison(this, other)){
            NonOverlappingConstraint castedOther = (NonOverlappingConstraint) other;
            return (v1.equals(castedOther.v1) && v2.equals(castedOther.v2) || v1.equals(castedOther.v2) && v2.equals(castedOther.v1));
        }
        return false;
    }

    // même éxplication que pour equals
    @Override
    public int hashCode() {
        return v1.hashCode() + v2.hashCode();
    }
}
