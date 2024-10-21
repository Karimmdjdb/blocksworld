package blocksworld.modelling.variables;

import java.util.Set;

public abstract class BooleanVariable extends Variable{
    public BooleanVariable(int id){
        super(id, createBooleanDomain());
    }

    public static Set<Object> createBooleanDomain(){
        return Set.of(true, false);
    }
}
