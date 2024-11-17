package blocksworld.cp.solvers;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import blocksworld.modelling.variables.Variable;
import blocksworld.modelling.constraints.Constraint;


// classe implémentant le filtrage avec un algorithme de cohérence d'arc.
public class ArcConsistency {
    private Set<Constraint> constraints;
    public ArcConsistency(Set<Constraint> constraints) throws IllegalArgumentException {
        Iterator<Constraint> it = constraints.iterator();
        while(it.hasNext()){
            Constraint c = it.next();
            if(c.getScope().size() > 2){
                throw new IllegalArgumentException("ni unaire ni binaire !");
            }
        }
        this.constraints = constraints;
    }

    // Vérifie la cohérence de noeud.
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domainsEvo) {
        // récupération des contraintes unaires
        Set<Constraint> unaryConstraints  = new HashSet<>();
        for(Constraint c : constraints) if(c.getScope().size() == 1) unaryConstraints.add(c);

        // on teste pour chaque variable, on supprime de son domaine les valeurs qui ne satisfont pas au moins une contrainte.
        for(Variable var : domainsEvo.keySet()) {
            Set<Object> toRemove = new HashSet<>();
            for(Object val : domainsEvo.get(var))
            for(Constraint c : unaryConstraints) {
                try {
                    if(!c.isSatisfiedBy(Map.of(var, val))) toRemove.add(val);
                }
                catch(Exception e) {}
            }
            domainsEvo.get(var).removeAll(toRemove);
        }

        // on retourne faux si au moins un domaine a été vidé.
        for(Variable var : domainsEvo.keySet()) if(domainsEvo.get(var).isEmpty()) return false;
        return true;
    }

    // Réduit la taille des domaines en supprimant les valeurs non viables.
    public boolean revise(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        // récupération des contraintes concernées.
        Set<Constraint> filteredConstraints = new HashSet<>();
        for(Constraint c : constraints) {
            if(c.getScope().equals(Set.of(v1, v2))) filteredConstraints.add(c);
        }

        // valeurs à supprimer (pour éviter une concurrence de modification).
        Set<Object> toRemove = new HashSet<>();

        // application de l'algorithme revise.
        boolean del = false;
        for(Object value1 : s1) {
            boolean viable = false;
            for(Object value2 : s2) {
                boolean allSatisfied = true;
                Map<Variable, Object> instanciation = Map.of(v1, value1, v2, value2);
                for(Constraint c : filteredConstraints) {
                    try {
                        if(!c.isSatisfiedBy(instanciation)) {
                            allSatisfied = false;
                            break;
                        }
                    }
                    catch(Exception e) {System.err.println(e.getMessage());}
                }
                if(allSatisfied) {
                    viable = true;
                    break;
                }
            }
            if(!viable) {
                toRemove.add(value1);
                del = true;
            }
        }
        s1.removeAll(toRemove);
        return del;
    }

    // Applique autant que possible Revise à tous es arcs sur lesquels il y a une contrainte.
    public boolean ac1(Map<Variable, Set<Object>> domainsEvo) {
        if(!enforceNodeConsistency(domainsEvo)) return false;
        Set<Variable> vars = domainsEvo.keySet();
        boolean change;
        do {
            change = false;
            for(Variable v1 : vars)
            for(Variable v2 : vars) {
                if(!v1.equals(v2) && revise(v1, domainsEvo.get(v1), v2, domainsEvo.get(v2))) change = true;
            }
        } while(change);

        for(Variable v : vars) if(domainsEvo.get(v).isEmpty()) return false;
        return true;
    }
}
