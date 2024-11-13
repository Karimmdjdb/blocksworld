package blocksworld.cp.heuristics;

import java.util.Set;
import java.util.List;
import blocksworld.modelling.variables.Variable;


// Interface pour les Heuristiques de valeur.
public interface ValueHeuristic {
    // Retourne le domaine ordonné de la meilleure valeur à la pire au sens de l'heuristique (sous forme de liste).
    public List<Object> ordering(Variable variable, Set<Object> domain);
}
