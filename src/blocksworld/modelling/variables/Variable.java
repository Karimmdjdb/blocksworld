package blocksworld.modelling.variables;

import java.util.Set;
import util.ClassComparator;

public abstract class Variable {
    private int id;
    private Set<Object> domain;
    
    public Variable(int id, Set<Object> domain){
        this.id = id;
        this.domain = domain;
    }

    public int getId(){
        return id;
    }

    public Set<Object> getDomain(){
        return domain;
    }

    @Override
    public boolean equals(Object other){
        if(ClassComparator.hardComparaison(this, other)){
            Variable otherVar = (Variable)other;
            return this.id == otherVar.id;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public String toString(){
        return String.format("<%s %d %s>", this.getClass().getSimpleName(), id, getDomain().toString()); 
    }
}