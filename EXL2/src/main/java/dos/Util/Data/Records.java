package dos.Util.Data;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class Records {

    private ImportsData imports;
    private SelfData self;

    public Records(ImportsData id, SelfData sd){
        imports = id;
        self = sd;
    }

    public List<String> getFieldNames() {
        return self.getFieldNames();
    }

    public List<String> getImportNames() {
        return imports.getNames();
    }

    public Result<List<String>> getConstructors(String name) {
        return imports.getConstructors(name);
    }

    public List<FunctionData> getFunctionData(String name) {
        return self.getFuncData(name);
    }

    public Result<String> getLongName(String shortName) {
        return imports.getLongPath(shortName);
    }

    public ImportsData getImports() {
        return imports;
    }

    public Result<String> getShortName(String longName) {
        return imports.getShortPath(longName);
    }

    public List<String> getDescsFromName(String name) {
        return self.getDescFromName(name);
    }

    public Maybe<MyError> addField(String name, String type) {
        return self.addField(name, type);
    }

    public Maybe<MyError> addFunction(String name, String type){
        return self.addFunction(name, type);
    }

    public Result<Pair<String,String>> getField(String name) {
        return self.getField(name);
    }

    public Result<String> getFieldType(String name, String fieldName) {
        var dataM = imports.getData(name);
        if(dataM.hasError())
            return Results.makeError(dataM.getError());
        var data = dataM.getValue();
        return data.getFieldType(fieldName);
    }
    
}
