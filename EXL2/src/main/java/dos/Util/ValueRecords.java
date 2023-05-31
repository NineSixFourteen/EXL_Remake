package dos.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.InfoClasses.ClassData;
import dos.Util.InfoClasses.ImportsData;

public class ValueRecords {

    /* Class responsible for keeping tracks of what differn't "values" are. 
        Values would be 
            Variables(Name, Type, Memory Location)
            Imports Long and short version
            Fileds(Name, Type, Memory Location)
            Functions(Name, Desctiption i.e. paramaters and return type)
    */ 

    private List<String> varNames; 
    private List<String> varTypes;
    private int nextMemory;
    private List<Integer> memoryLocation; 
    private List<Boolean> isField; 
    private List<Pair<String, String>> importNames;
    private ImportsData importData;
    private List<Pair<String, String>> functions; 

    public ValueRecords(){
        varNames = new ArrayList<>();
        varTypes = new ArrayList<>();
        memoryLocation = new ArrayList<>();
        isField = new ArrayList<>();
        importNames = new ArrayList<>();
        nextMemory = 0;
    }

    public ValueRecords(List<Pair<String,String>> parameters){
        varNames = new ArrayList<>();
        varTypes = new ArrayList<>();
        memoryLocation = new ArrayList<>();
        isField = new ArrayList<>();
        importNames = new ArrayList<>();
        nextMemory = 0;
        for(Pair<String,String> param : parameters){
            addVariable(param.getValue0(), param.getValue1());
        }
    }

    // No check if exists as sould of been done during validation stage
    public Result<Triplet<String,String,Integer>> getVar(String name){
        var ind = IntStream.range(0, varNames.size())
        .filter(i -> name.equals(varNames.get(i)))
        .findFirst();
        if(ind.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("Can not find variable " + name, 7));
        }
        int i = ind.getAsInt();
        return Results.makeResult(new Triplet<String,String,Integer>(varNames.get(i), varTypes.get(i), memoryLocation.get(i)));
    }

    public boolean isField(String name){
        var ind = IntStream.range(0, varNames.size())
        .filter(i -> name.equals(varNames.get(i)))
        .findFirst();
        int i = ind.getAsInt();
        return isField.get(i);
    }

    public Result<List<String>> getConstuctors(String name){
        return importData.getConstructors(name);
    }

    public Result<String> getFullImport(String shortName){
        Optional<Pair<String,String>> itemMaybe = importNames.stream().filter(x -> x.getValue0().equals(shortName)).findFirst();
        if(itemMaybe.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("No such import - "+ shortName,8));
        } else {
            return Results.makeResult(itemMaybe.get().getValue1());
        }
    }

    public List<Pair<String, String>> getFunctions() {
        return functions;
    }

    public List<String> getDescFromName(String funcName){
        return functions.stream().filter(x -> x.getValue0().equals(funcName)).map( x -> x.getValue1()).collect(Collectors.toList());
    }

    public ClassData getImportInfo(String name){
        return importData.getData(name);
    }

    public Maybe<MyError> addVariable(String name, String type){
        this.varNames.add(name);
        this.varTypes.add(type);
        this.memoryLocation.add(nextMemory);
        increaseMemory(type);
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

    public Result<String> getType(String name, String partialDescription, ValueRecords records) {
        List<String> descriptions = functions.stream()
                        .filter(x -> x.getValue0().equals(name))
                        .map(x -> x.getValue1())
                        .collect(Collectors.toList());
        for(String description : descriptions){
            if(partialMatch(description, partialDescription)){
                return DescriptionMaker.fromASM(description.substring(description.lastIndexOf(')') + 1), records );
            }
        }
        return  Results.makeError(ErrorFactory.makeLogic("Type unknown for function " + name + " with description " + partialDescription,20));
    }

    private boolean partialMatch(String description, String partialDescription) {
        return description.substring(0, description.lastIndexOf(')')).equals(partialDescription);
    }

    public Result<String> getShortImport(String longName){
        Optional<Pair<String,String>> itemMaybe = importNames.stream().filter(x -> x.getValue1().equals(longName)).findFirst();
        if(itemMaybe.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("No such import - "+ longName,8));
        } else {
            return Results.makeResult(itemMaybe.get().getValue1());
        }
    }

    public Maybe<MyError> addFunction(String name, String desc) {
        List<String> descs = getDescFromName(name);
        for(String des : descs){
            if(des == desc){
                return new Maybe<>(ErrorFactory.makeLogic("Function with this name and desciption already exists",21));
            }
        }
        functions.add(new Pair<String,String>(name,desc));
        return new Maybe<>();
    }
    
    
    
}
