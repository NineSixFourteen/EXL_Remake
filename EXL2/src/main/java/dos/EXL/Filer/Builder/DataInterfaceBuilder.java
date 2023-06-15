package dos.EXL.Filer.Builder;

import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.SelfData;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.Util.Interaces.DataInterface;
public class DataInterfaceBuilder {

    private VariableData vars;
    private ImportsData imports;
    private SelfData self;
    private String name;

    public DataInterfaceBuilder(){
        vars = new VariableData();
        imports = new ImportsData();
        self = new SelfData();
        name = "";
    }

    public DataInterfaceBuilder addImports(ImportsData imports){
        this.imports = imports;
        return this;
    }

    public DataInterfaceBuilder addSelf(SelfData self){
        this.self = self;
        return this;
    }

    public DataInterfaceBuilder addName(String name){
        this.name = name;
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
        return new DataInterface(name, imports, self, vars);
    }
    
}