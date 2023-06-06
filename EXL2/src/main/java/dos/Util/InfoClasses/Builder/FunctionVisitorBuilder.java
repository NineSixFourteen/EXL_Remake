package dos.Util.InfoClasses.Builder;

import dos.Util.InfoClasses.FunctionVisitor;
import dos.Util.InfoClasses.ImportsData;
import dos.Util.InfoClasses.Records;
import dos.Util.InfoClasses.SelfData;

public class FunctionVisitorBuilder {

    private FunctionVisitor fv ;
    private ImportsData imports = new ImportsData();
    private SelfData self = new SelfData();

    public FunctionVisitorBuilder(){
        fv = new FunctionVisitor(new Records(imports, self),null);
    }

    public FunctionVisitorBuilder addImports(ImportsData imports){
        this.imports = imports;
        fv = new FunctionVisitor(new Records(imports, self),null);
        return this;
    }

    public FunctionVisitorBuilder addSelf(SelfData self){
        this.self = self;
        fv = new FunctionVisitor(new Records(imports, self),null);
        return this;
    }

    public FunctionVisitorBuilder addField(String name, String type){
        fv.addField(name, type);
        return this;
    }

    public FunctionVisitorBuilder addVar(String name, String type){
        fv.addVariable(name, type);
        return this;
    }

    public FunctionVisitor build(){
        return fv;
    }
    
}
