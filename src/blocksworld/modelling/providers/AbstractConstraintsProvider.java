package blocksworld.modelling.providers;

import java.util.HashSet;
import java.util.Set;

import blocksworld.modelling.constraints.Constraint;

public abstract class AbstractConstraintsProvider implements ConstraintsProvider{
    protected VariablesProvider vm;
    protected Set<Constraint> constraints;

    public AbstractConstraintsProvider(int blocksCount, int stacksCount){
        vm = new VariablesProvider(blocksCount, stacksCount);
        constraints = new HashSet<>();
    }

    public Set<Constraint> getConstraints(){
        return constraints;
    }
}
