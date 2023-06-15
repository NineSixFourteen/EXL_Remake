package dos.Util.Data.Imports;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.DescriptionMaker;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Data.ClassData;

public class ImportsData {

    List<ImportInfo> imports;

    public ImportsData(){
        imports = new ArrayList<>();
    }

    public Result<ClassData> getData(String name) {
        Optional<ClassData> data = imports.stream()
                                        .filter(imp -> imp.is(name))
                                        .map(imp -> imp.getData())
                                        .findFirst();
        if(data.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("Class" + name + " has not been imported", 8));
        }
        return Results.makeResult(data.get());
    }

    public List<ImportInfo> getImports() {
        return imports;
    }

    public Result<String> getLongPath(String name){
        Optional<String> data = imports.stream()
                                    .filter(imp -> imp.is(name))
                                    .map(imp -> imp.getLongName())
                                    .findFirst();
        if(data.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("Class" + name + " has not been imported", 8));
        }
        return Results.makeResult(data.get());
    }

    public Result<String> getShortPath(String name){
        Optional<String> data = imports.stream()
                                    .filter(imp -> imp.is(name))
                                    .map(imp -> imp.getLongName())
                                    .findFirst();
        if(data.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("Class" + name + " has not been imported", 8));
        }
        return Results.makeResult(data.get());
    }

    public Result<List<String>> getConstructors(String name) {
        var data = getData(name);
        if(data.hasError())
            return Results.makeError(ErrorFactory.makeLogic("Class " + name + " has not been imported", 8));
        var constructors = data.getValue().getConstructors();
        return Results.makeResult(constructors.stream().map(x -> x.getDesc()).collect(Collectors.toList())); 
    }

    public void addImport(String shortName, String longName, ClassData cd){
        imports.add(new ImportInfo(shortName,longName,cd));
    }

    public List<String> getNames() {
        return imports.stream().map(imp -> imp.getShortName()).toList();
    }

    public Result<String> getFunctionType(String shortName, String name, String desc) {
        var data = getData(shortName);
        if(data.hasError())
            return Results.makeError(data.getError());
        var potFuncs = data.getValue().getFunctionsFromName(name);
        var dess = potFuncs.stream()
                    .filter(funcs -> funcs.getDesc().substring(0, funcs.getDesc().lastIndexOf(")")+ 1).equals(desc))
                    .findFirst();
        if(dess.isEmpty())
            return Results.makeError(ErrorFactory.makeLogic("Could not find a function called " + name + " with a description of " + desc + " in the class " + shortName, 6)); 
        String type = dess.get()
                        .getDesc()
                        .substring(
                           dess.get()
                            .getDesc()
                            .lastIndexOf(")") + 1
                        );
        return DescriptionMaker.fromASM(type, this);
    }

    public Result<String> getFieldTy(String name, String fieldName) {
        var data = getData(name);
        if(data.hasError())
            return Results.makeError(data.getError());
        return data.getValue().getFieldType(fieldName);
    }

}
