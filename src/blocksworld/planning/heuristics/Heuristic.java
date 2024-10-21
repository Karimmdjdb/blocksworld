package blocksworld.planning.heuristics;

import java.util.Map;
import blocksworld.modelling.variables.Variable;

public interface Heuristic {
    public float estimate(Map<Variable, Object> state);
}
