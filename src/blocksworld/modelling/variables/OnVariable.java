package blocksworld.modelling.variables;

import java.util.HashSet;
import java.util.Set;

public class OnVariable extends Variable{
    public OnVariable(int id, Set<Object> domain){
        super(id, OnVariable.exludeSelfFromDomain(domain, id));
    }

    private static Set<Object> exludeSelfFromDomain(Set<Object> domain, int id){
        Set<Object> newDomain = new HashSet<>(domain);
        newDomain.remove(id);
        return newDomain;
    }

    @Override
    public String toString(){
        return String.format("On%d", getId());
    }
}
