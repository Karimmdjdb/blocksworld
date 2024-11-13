package blocksworld.cp.solvers;

import java.util.Map;
import blocksworld.modelling.variables.Variable;

public interface Solver {
    // Retourne une solution pour le CSP.
    public Map<Variable, Object> solve();
}