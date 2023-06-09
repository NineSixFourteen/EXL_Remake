package dos.Util.Data.Builder;

import dos.Util.Data.ImportsData;
import dos.Util.Data.Records;
import dos.Util.Data.SelfData;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class FunctionVisitorBuilder {

    private DataInterface DI ;
    private ImportsData imports = new ImportsData();
    private SelfData self = new SelfData();

    public FunctionVisitorBuilder(){
        DI = new DataInterface(new Records(imports, self));
    }

    public FunctionVisitorBuilder addImports(ImportsData imports){
        this.imports = imports;
        DI = new DataInterface(new Records(imports, self));
        return this;
    }

    public FunctionVisitorBuilder addSelf(SelfData self){
        this.self = self;
        DI = new DataInterface(new Records(imports, self));
        return this;
    }

    public FunctionVisitorBuilder addField(String name, String type){
        DI.addField(name, type);
        return this;
    }

    public FunctionVisitorBuilder addVar(String name, String type){
        DI.addVariable(name, type,null);
        return this;
    }

    public DataInterface build(){
        return DI;
    }
    
}
