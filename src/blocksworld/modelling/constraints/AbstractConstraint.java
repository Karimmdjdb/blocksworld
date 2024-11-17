package blocksworld.modelling.constraints;

import java.util.Map;
import blocksworld.modelling.variables.Variable;

public abstract class AbstractConstraint implements Constraint{
        // lance une exception si au moins une variable du scope n'est pas couverte par l'instanciation
        public void checkIfScopeIsTreated(Map<Variable, Object> instanciation){
            for(Variable var : getScope()){
                if(!instanciation.containsKey(var)) throw new IllegalArgumentException();
            }
        }
}
