package blocksworld.modelling.variables;

public class FixedVariable extends BooleanVariable{
    public FixedVariable(int id){
        super(id);
    }

    @Override
    public String toString(){
        return String.format("Fixed%d", getId());
    }
}
