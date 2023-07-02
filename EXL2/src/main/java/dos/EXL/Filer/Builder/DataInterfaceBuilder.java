package dos.EXL.Filer.Builder;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.SelfData;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;
public class DataInterfaceBuilder {

    private VariableData vars;
    private ImportsData imports;
    private SelfData self;

    public DataInterfaceBuilder(String name){
        vars = new VariableData();
        imports = new ImportsData();
        self = new SelfData(name);
    }

    public DataInterfaceBuilder addImports(ImportsData imports){
        this.imports = imports;
        return this;
    }

    public DataInterfaceBuilder addSelf(SelfData self){
        this.self = self;
        return this;
    }

    public DataInterfaceBuilder addField(String name,String type){
        self.addField(name, type);
        return this;
    }

    public DataInterfaceBuilder addVar(Variable var){
        vars.add(var);
        return this;
    }

    public DataInterface build(){
        return new DataInterface(imports, self, vars);
    }
    
}
