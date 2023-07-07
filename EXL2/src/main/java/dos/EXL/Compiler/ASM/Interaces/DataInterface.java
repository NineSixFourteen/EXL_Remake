package dos.EXL.Compiler.ASM.Interaces;

import java.util.List;

import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.SelfData;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;

public class DataInterface {

    // The object that each compileASM will accept 
    
    private ImportsData imports;
    private SelfData self;
    private VariableData vars; 


    public DataInterface(String name){
        imports = new ImportsData();
        self = new SelfData(name);
        vars = new VariableData();
    }

    public DataInterface(ImportsData Imports, SelfData Self, VariableData Vars){
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
        return self.getName();
    }

    public boolean isStatic(String name, String desc) {
        return self.isFuncStatic(name, desc);
    }

}
