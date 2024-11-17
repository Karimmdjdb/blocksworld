package blocksworld.datamining.variables;

import util.ClassComparator;

public class OnVariable extends blocksworld.modelling.variables.BooleanVariable {
    private int id2;
    public OnVariable(int b, int b2) {
        super(b);
        this.id2 = b2;
    }

    @Override
    public String toString() {
        return "On" + getId() + "," + id2;
    }

    @Override
    public boolean equals(Object other){
        if(ClassComparator.hardComparaison(this, other)){
            OnVariable otherVar = (OnVariable)other;
            return this.getId() == otherVar.getId() && this.id2 == otherVar.id2;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return getId() * id2 + this.getClass().getName().hashCode();
    }
}
