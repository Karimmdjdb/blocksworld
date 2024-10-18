package blocksworld.modelling.constraints;

import java.util.Map;
import blocksworld.modelling.variables.Variable;

public abstract class AbstractConstraint implements Constraint{
        public void checkIfScopeIsTreated(Map<Variable, Object> instanciations){
            for(Variable var : getScope()){
                if(!instanciations.containsKey(var)) throw new IllegalArgumentException();
            }
        }
}
