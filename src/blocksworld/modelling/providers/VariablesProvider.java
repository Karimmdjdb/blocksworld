package blocksworld.modelling.providers;

import java.util.HashSet;
import java.util.Set;

import blocksworld.modelling.variables.FixedVariable;
import blocksworld.modelling.variables.FreeVariable;
import blocksworld.modelling.variables.OnVariable;
import blocksworld.modelling.variables.Variable;

public class VariablesProvider {
    private Set<Object> allElementsDomain = new HashSet<>();
    private Set<Variable> onVariables= new HashSet<>(), fixedVariables = new HashSet<>(), freeVariables= new HashSet<>(), variables = new HashSet<>();  

    public VariablesProvider(int blocksCount, int stacksCount){
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

    public Set<Variable> getOnVariables(){
        return onVariables;
    }

    public Set<Variable> getFixedVariables(){
        return fixedVariables;
    }

    public Set<Variable> getFreeVariables(){
        return freeVariables;
    }


}
