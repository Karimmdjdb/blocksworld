package blocksworld.cp.heuristics;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import blocksworld.modelling.variables.Variable;

// Heuristique aléatoire sur les valeurs.
public class RandomValueHeuristic implements ValueHeuristic {
    private Random random;

    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    // Retourne une liste melangée pseudo-aléatoirement qui représente les valeurs du domaine.
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain) {
        List<Object> domainList = new ArrayList<>(domain);
        LinkedList<Object> orderedDomainList = new LinkedList<>();
        while(!domainList.isEmpty()) {
            int i = random.nextInt(domainList.size());
            orderedDomainList.offerLast(domainList.remove(i));
        }
        return orderedDomainList;
    }
}
