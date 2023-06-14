package dos.Util.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.type.ErrorType;

import org.javatuples.Pair;

import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.DescriptionMaker;
import dos.Util.Result;
import dos.Util.Results;


public class ImportsData {

    List<Pair<String, String>> importPaths; 
    List<Pair<String, ClassData>> classes;

    public ImportsData(){
        importPaths = new ArrayList<>();
        classes = new ArrayList<>();
    }

    public Result<ClassData> getData(String name) {
        List<Pair<String,ClassData>> data = classes.stream().filter( x -> x.getValue0().equals(name)).collect(Collectors.toList());
        if(data.size() == 0){
            return Results.makeError(ErrorFactory.makeLogic("Class" + name + " has not been imported", 8));
        }
        return Results.makeResult(data.get(0).getValue1());
    }

    public List<Pair<String, String>> getImportPaths() {
        return importPaths;
    }

    public List<Pair<String, ClassData>> getClasses() {
        return classes;
    }

    public Result<String> getLongPath(String name){
        List<Pair<String,String>> data = importPaths.stream().filter( x -> x.getValue0().equals(name)).collect(Collectors.toList());
        if(data.size() == 0){
            return Results.makeError(ErrorFactory.makeLogic("Class " + name + " has not been imported", 8));
        }
        return Results.makeResult(data.get(0).getValue1());
    }

    public Result<String> getShortPath(String name){
        List<Pair<String,String>> data = importPaths.stream().filter( x -> x.getValue1().equals(name)).collect(Collectors.toList());
        if(data.size() == 0){
            return Results.makeError(ErrorFactory.makeLogic("Class " + name + " can not be found", 8));
        }
        return Results.makeResult(data.get(0).getValue0());
    }

    public Result<List<String>> getConstructors(String name) {
        var data = getData(name);
        if(data.hasError()){
            return Results.makeError(ErrorFactory.makeLogic("Class " + name + " has not been imported", 8));
        }
        var constructors = data.getValue().getConstructors();
        return Results.makeResult(constructors.stream().map(x -> x.Desc).collect(Collectors.toList())); 
    }

    public void addImport(String shortName, String longName, ClassData cd){
        importPaths.add(new Pair<>(shortName, longName));
        classes.add(new Pair<>(shortName, cd));
    }

    public List<String> getNames() {
        return importPaths.stream().map(x -> x.getValue0()).toList();
    }

    public Result<String> getFunctionType(String shortName, String name, String desc) {
        var cd = classes.stream()
                    .filter(cla -> cla.getValue0().equals(shortName))
                    .map(x -> x.getValue1())
                    .findFirst();
        if(cd.isEmpty())
            return Results.makeError(ErrorFactory.makeLogic("Could not find a class called " + shortName, 2));
        ClassData data = cd.get();
        var potFuncs = data.getFunctionsFromName(name);
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
        var classDa = classes.stream()
                                .filter(cla -> cla.getValue0().equals(name))
                                .map(x -> x.getValue1())
                                .findFirst();
        if(classDa.isEmpty())
            return Results.makeError(ErrorFactory.makeLogic("unable to find the class name", 0));
        return classDa.get().getFieldType(fieldName);
    }

}
