package dos.Util.InfoClasses;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dos.Util.Result;
import dos.Util.Results;


public class ImportsData {

    HashMap<String, String> importPaths; 
    HashMap<String, ClassData> classes;

    public ClassData getData(String name) {
        return classes.get(name);
    }

    public String getPath(String name){
        return importPaths.get(name);
    }

    public Result<List<String>> getConstructors(String name) {
        ClassData data = classes.get(name);
        if(data == null){
            return Results.makeError("No class by the name of " + name);
        }
        return Results.makeResult(data.getConstructors().stream().map(x -> x.Desc).collect(Collectors.toList())); 
    }


}
