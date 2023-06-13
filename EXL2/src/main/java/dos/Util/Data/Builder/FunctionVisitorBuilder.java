package dos.Util.Data.Builder;

import dos.Util.Data.ImportsData;
import dos.Util.Data.Records;
import dos.Util.Data.SelfData;
import dos.Util.Data.Variable;
import dos.Util.Data.VariableData;
import dos.Util.Interaces.DataInterface;
public class FunctionVisitorBuilder {

    private VariableData vars;
    private ImportsData imports;
    private SelfData self;
    private String name;

    public FunctionVisitorBuilder(){
        vars = new VariableData();
        imports = new ImportsData();
        self = new SelfData();
        name = "";
    }

    public FunctionVisitorBuilder addImports(ImportsData imports){
        this.imports = imports;
        return this;
    }

    public FunctionVisitorBuilder addSelf(SelfData self){
        this.self = self;
        return this;
    }

    public FunctionVisitorBuilder addName(String name){
        this.name = name;
        return this;
    }

    public FunctionVisitorBuilder addField(String name,String type){
        self.addField(name, type);
        return this;
    }

    public FunctionVisitorBuilder addVar(Variable var){
        vars.add(var);
        return this;
    }

    public DataInterface build(){
        return new DataInterface(name, imports, self, vars);
    }
    
}
