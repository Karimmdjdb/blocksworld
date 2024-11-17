package blocksworld.datamining.providers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blocksworld.datamining.variables.OnTableVariable;
import blocksworld.datamining.variables.OnVariable;
import blocksworld.modelling.variables.BooleanVariable;
import blocksworld.modelling.variables.FixedVariable;
import blocksworld.modelling.variables.FreeVariable;

public class BooleanVariablesProvider {
    private int blocksCount, stacksCount;

    public BooleanVariablesProvider(int blocksCount, int stacksCount) {
        this.blocksCount = blocksCount;
        this.stacksCount = stacksCount;
    }

    public Set<BooleanVariable> getBooleanVariables() {
        Set<BooleanVariable> booleanVars = new HashSet<>();
        for(int b = 0; b < blocksCount; b++) {
            for(int b2 = 0; b2 < blocksCount; b2++) {
                if(b != b2) booleanVars.add(new OnVariable(b, b2));
            }
            for(int p = -stacksCount; p < 0; p++) {
                booleanVars.add(new OnTableVariable(b, p));
            }
            booleanVars.add(new FixedVariable(b));
        }
        for(int p = -stacksCount; p < 0; p++) {
            booleanVars.add(new FreeVariable(p));
        }
        return booleanVars;
    }

    public Set<BooleanVariable> getInstance(List<List<Integer>> state) {
        Set<BooleanVariable> instance = new HashSet<>();
        int stacks = state.size();
        for(int p=1; p<=stacks; p++) {
            if(state.get(p-1).isEmpty()) instance.add(new FreeVariable(-p));
            else {
                int lastBolc = state.get(p-1).size();
                instance.add(new OnTableVariable(state.get(p-1).get(0), -p));
                for(int b=0; b<lastBolc; b++) {
                    if(b != lastBolc-1) instance.add(new FixedVariable(state.get(p-1).get(b)));
                    if(b > 0) instance.add(new OnVariable(state.get(p-1).get(b), state.get(p-1).get(b-1)));
                }
            }
        }
        return instance;
    }
}
