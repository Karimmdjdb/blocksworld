package blocksworld.modelling;

import java.util.HashSet;
import java.util.Set;

import blocksworld.modelling.variables.FixedVariable;
import blocksworld.modelling.variables.FreeVariable;
import blocksworld.modelling.variables.OnVariable;
import blocksworld.modelling.variables.Variable;

public class VariablesManager {
    private int blocksCount, stacksCount;
    private Set<Object> allElementsDomain = new HashSet<>();
    private Set<Variable> onVariables= new HashSet<>(), fixedVariables = new HashSet<>(), freeVariables= new HashSet<>(), variables = new HashSet<>();  

    public VariablesManager(int blocksCount, int stacksCount){
        this.blocksCount = blocksCount;
        this.stacksCount = stacksCount;
        for(int i = -stacksCount; i<0; i++){
            allElementsDomain.add(i);
        }
        for(int i = 0; i < blocksCount; i++){
            allElementsDomain.add(i);
        }

        for(Object id : allElementsDomain){
            Integer intId = (Integer) id;
            if(intId < 0){
                freeVariables.add(new FreeVariable(intId));
                variables.add(new FreeVariable(intId));
            }
            else{
                onVariables.add(new OnVariable(intId, allElementsDomain));
                variables.add(new OnVariable(intId, allElementsDomain));
                fixedVariables.add(new FixedVariable(intId));
                variables.add(new FixedVariable(intId));
            }
        }
    }

    public Set<Variable> getVariables(){
        return variables;
    }


}
