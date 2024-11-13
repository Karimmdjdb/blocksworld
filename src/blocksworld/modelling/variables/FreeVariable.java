package blocksworld.modelling.variables;

public class FreeVariable extends BooleanVariable{
    public FreeVariable(int id){
        super(id);
    }

    @Override
    public String toString(){
        return String.format("Free%d", getId());
    }
}
