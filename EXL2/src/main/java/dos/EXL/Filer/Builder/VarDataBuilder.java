package dos.EXL.Filer.Builder;

import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;

public class VarDataBuilder {

    private VariableData data; 

    public VarDataBuilder(){
        data = new VariableData();
    }

    public VarDataBuilder addVar(Variable var){
        data.add(var);
        return this;
    }

    public VariableData build(){
        return data;
    }
    
}
