package dos.Util.Interaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.objectweb.asm.Label;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Data.ImportsData;
import dos.Util.Data.Records;
import dos.Util.Data.SelfData;

public class DataInterface {

    // The object that each compileASM will accept 
    // Contains MethodVisitor to add instuctions to 
    // Contains info of each variable in a function aswell fields and import info inside of  
    private String name; 
    private Records records;
    private List<String> varNames; 
    private List<String> varTypes;
    private int nextMemory;
    private int maxVars;
    private List<Integer> memoryLocation; 
    private HashMap<String, Maybe<Label>> varLabels;

    public DataInterface(String n){
        name = n;
        this.records = new Records(new ImportsData(), new SelfData());
        varNames = new ArrayList<>();
        varTypes = new ArrayList<>(); 
        memoryLocation = new ArrayList<>();
        nextMemory = 0;
        maxVars = 0;
        varLabels = new HashMap<>();
    }

    public DataInterface(String n,Records records) {
        name = n;
        this.records = records;
        varNames = new ArrayList<>();
        varTypes = new ArrayList<>(); 
        memoryLocation = new ArrayList<>();
        nextMemory = 0;
        maxVars = 0;
        varLabels = new HashMap<>();
    }

    public DataInterface(Records records, List<Pair<String,String>> params) {
        this.records = records;
        varNames = new ArrayList<>();
        varTypes = new ArrayList<>(); 
        memoryLocation = new ArrayList<>();
        nextMemory = 0;
        varLabels = new HashMap<>();
        for(Pair<String,String> param : params){
            addVariable(param.getValue0(),param.getValue1(), null);
        }
    }

    public Result<Triplet<String,String,Integer>> getVar(String name){
        var ind = IntStream.range(0, varNames.size())
        .filter(i -> name.equals(varNames.get(i)))
        .findFirst();
        if(ind.isEmpty()){
            var se = records.getField(name);
            if(se.hasValue()){
                return Results.makeResult(new Triplet<>(se.getValue().getValue0(),se.getValue().getValue1(),-1));
            }
            return Results.makeError(ErrorFactory.makeLogic("Variable" + name + " not found", 7));
        }
        int i = ind.getAsInt();
        return Results.makeResult(new Triplet<String,String,Integer>(varNames.get(i), varTypes.get(i), memoryLocation.get(i)));
    }

    public Maybe<MyError> dropVariable(String name){
        var ind = IntStream.range(0, varNames.size())
            .filter(i -> name.equals(varNames.get(i)))
            .findFirst();
        if(ind.isEmpty()){
            return new Maybe<MyError>(ErrorFactory.makeLogic("Variable " + name + " was not found for deletion",7));
        }
        int i = ind.getAsInt();
        varNames.remove(i);
        varTypes.remove(i);
        int mem = memoryLocation.remove(i);
        if(mem < nextMemory)
            nextMemory = mem;
        return new Maybe<>();
    }

    private void increaseMemory(String type) {
        switch(type){
            case "double":
            case "long":
                nextMemory += 2;
                break;
            default:
                nextMemory++;
        }
    }

    public Maybe<MyError> addVariable(String name,String type, Label l){
        List<String> names = this.varNames.stream().filter(x -> x.equals(name)).toList();
        if(names.size() > 0)
            return new Maybe<>(ErrorFactory.makeLogic("Duplicate variable name used " + name, 22));
        List<String> fields = records.getFieldNames();
        if(fields.size() > 0)
            return new Maybe<>(ErrorFactory.makeLogic("Duplicate variable name used " + name, 22));
        if(!basicType(type)){
            List<String> imports  = records.getImportNames();
            if(imports.size() < 1)
                return new Maybe<>(ErrorFactory.makeLogic("Unable to find the type of ", 8));
        }
        this.varNames.add(name);
        this.varTypes.add(type);
        this.memoryLocation.add(nextMemory);
        if(varNames.size() > maxVars) 
            maxVars = varNames.size();
        if(l == null)
            this.varLabels.put(name, new Maybe<>());
        else 
            this.varLabels.put(name, new Maybe<Label>(l));
        increaseMemory(type);
        return new Maybe<>();
    }

    private boolean basicType(String type) {
        switch(type){
            case "String":
            case "long":
            case "double":
            case "short":
            case "int":
            case "float":
            case "char":
            case "boolean":
                return true;
            default:
                return false;
        }
    }

    public Result<List<String>> getConstuctors(String name){
        return records.getConstructors(name);
    }

    public Result<String> getFullImport(String shortName){
        return records.getLongName(shortName);
    }

    public List<String> getDescriptionsFromName(String name){
        return records.getDescsFromName(name);
    }

    public Result<String> getType(String name, String partialDescription) {
        List<String> descriptions = getDescriptionsFromName(name);
        for(String description : descriptions){
            if(partialMatch(description, partialDescription)){
                return DescriptionMaker.fromASM(description.substring(description.lastIndexOf(')') + 1, description.length()), records.getImports() );
            }
        }
        return  Results.makeError(ErrorFactory.makeLogic("Type unknown for function " + name + " with description " + partialDescription,20));
    }

    private boolean partialMatch(String description, String partialDescription) {
        String partial = description.substring(description.lastIndexOf('(') , description.lastIndexOf(")") + 1 );
        return partial.equals(partialDescription);
    }

    public Result<String> getShortImport(String longName){
        return records.getShortName(longName);
    }

    public Result<String> getfuncType(String shortName, FunctionExpr func) {
        var findClass =  records.getImports()
                                .getClasses()
                                .stream()
                                .filter(x -> x.getValue0().equals(shortName))
                                .map(x -> x.getValue1())
                                .toList();
                                
        if(findClass.size() ==  0){
            return Results.makeError(ErrorFactory.makeLogic("Unable to find the import class of " + shortName, 8));
        }
        var classData = findClass.get(0);
        var functions = classData.getFunctionsFromName(func.getName());
        var partialDesc = DescriptionMaker.partial(func.getParams(), this);
        if(partialDesc.hasError())
            return partialDesc;
        var matchingFunctions = functions.stream()
            .filter(x -> x.getDesc().substring(0, x.getDesc().lastIndexOf(")") + 1).equals(partialDesc.getValue()))
            .map(x -> x.getDesc())
            .toList();
        if(matchingFunctions.size() == 0)
            return Results.makeError(ErrorFactory.makeLogic("unable to find the function " + func.makeString() + " in the class" + shortName,6));
        String s = matchingFunctions.get(0).substring(matchingFunctions.get(0).lastIndexOf(")") + 1);
        return DescriptionMaker.fromASM(s, records.getImports());
    }

    public ImportsData getImports() {
        return records.getImports();
    }

    public Maybe<MyError> addField(String name,String type) {
        return records.addField(name,type);
    }

    public Result<String> getFieldType(String name, String fieldName) {
        return records.getFieldType(name, fieldName);
    }

    public int getNextMemort(){
        return nextMemory;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic(String name, String desc) {
        var pots = records.getFunctionData(name);
        return pots.stream()
                .filter(x -> x.getDesc().substring(0, x.getDesc().lastIndexOf(")")).equals(desc))
                .map(x -> x.getTags().stream()
                            .filter(y -> y == Tag.Static)
                            .toList()
                            .size() < 1)
                .findFirst()
                .isPresent();
    }

}
