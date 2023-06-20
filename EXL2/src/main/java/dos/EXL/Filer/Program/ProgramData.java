package dos.EXL.Filer.Program;

import java.util.List;
import java.util.Optional;

import org.javatuples.Pair;

import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.DataInterface;

public class ProgramData {

    private ImportsData imports; 
    private SelfData self;
    private List<Pair<String,VariableData>> methods;

    public ProgramData(ImportsData imports, SelfData self, List<Pair<String,VariableData>> methods){
        this.imports = imports;
        this.self = self;
        this.methods = methods;
    }

    public Result<DataInterface> getDataInterface(String key){
        if(key.equals("fields")){
            return Results.makeResult(new DataInterface(key, imports, self, new VariableData()));
        }
        Optional<VariableData> id = methods.stream()
                    .filter(met -> met.getValue0().equals(key))
                    .map(met -> met.getValue1())
                    .findFirst();
        if(id.isEmpty())
            return Results.makeError(ErrorFactory.makeLogic("Unable to find a method with the key " + key , 0));
        return Results.makeResult(new DataInterface(key, imports, self, id.get()));
    }

    public ImportsData getImports() {
        return imports;
    }

    public SelfData getSelf() {
        return self;
    }
    
}
