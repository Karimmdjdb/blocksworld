package blocksworld.modelling.variables;

import java.util.HashSet;
import java.util.Set;

public class OnVariable extends Variable{
    public OnVariable(int id, Set<Object> generalDomain){
        // va créer une variable dont le domaine sera {-m,... n-1}\{id}
        super(id, OnVariable.exludeSelfFromDomain(generalDomain, id));
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
