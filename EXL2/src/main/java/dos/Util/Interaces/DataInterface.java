package dos.Util.Interaces;

import java.util.List;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Data.ImportsData;
import dos.Util.Data.SelfData;
import dos.Util.Data.Variable;
import dos.Util.Data.VariableData;

public class DataInterface {

    // The object that each compileASM will accept 
    // Contains MethodVisitor to add instuctions to 
    // Contains info of each variable in a function aswell fields and import info inside of  
    private String name; 
    private ImportsData imports;
    private SelfData self;
    private VariableData vars; 


    public DataInterface(String n){
        name = n;
        imports = new ImportsData();
        self = new SelfData();
        vars = new VariableData();
    }

        public DataInterface(String n, ImportsData Imports, SelfData Self, VariableData Vars){
        name = n;
        imports = Imports;
        self = Self;
        vars = Vars;
    }

    public List<Variable> getVarsFromName(String name){
        return vars.get(name);
    }

    public Result<Variable> getVar(String name, int startLine){
        var x = vars.get(name,startLine);
        if(x.hasValue())
            return x;
        var f = self.getField(name);
        return f;
    }

    public void addVariable(Variable var){
        vars.add(var);
    }

    public Result<List<String>> getConstuctors(String name){
        return imports.getConstructors(name);
    }

    public Result<String> getFullImport(String shortName){
        return imports.getLongPath(shortName);
    }

    public List<String> getDescriptionsFromName(String name){
        return self.getDescFromName(name);
    }

    public Result<String> getType(String name, String partialDescription) {
        return self.getFuncType(name, partialDescription,imports);
    }

    public Result<String> getShortImport(String longName){
        return imports.getShortPath(longName);
    }

    public Result<String> getfuncType(String shortName, String name, String desc) {
        return imports.getFunctionType(shortName, name, desc);
    }

    public ImportsData getImports() {
        return imports;
    }

    public Maybe<MyError> addField(String name,String type) {
        return self.addField(name,type);
    }

    public Result<String> getFieldTypeSelf(String fieldName) {
        return self.getFieldTy(fieldName);
    }

    public Result<String> getFieldType(String name, String fieldName) {
        return imports.getFieldTy(name, fieldName);
    }

    public String getName() {
        return name;
    }

    public boolean isStatic(String name, String desc) {
        return self.isFuncStatic(name, desc);
    }

}
