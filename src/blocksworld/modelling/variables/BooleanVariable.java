package blocksworld.modelling.variables;

import java.util.Set;
import java.util.HashSet;

public abstract class BooleanVariable extends Variable{
    public BooleanVariable(int id){
        super(id, createBooleanDomain());
    }

    public static Set<Object> createBooleanDomain(){
        Set<Object> booleanDomain = new HashSet<>();
        booleanDomain.add(false);
        booleanDomain.add(true);
        return booleanDomain;
    }
}
