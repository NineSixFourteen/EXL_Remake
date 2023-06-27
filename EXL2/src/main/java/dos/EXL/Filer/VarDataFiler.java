package dos.EXL.Filer;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Function;
import dos.EXL.Types.Line;
import dos.Util.Result;
import dos.Util.Results;

public class VarDataFiler {

    public static Result<List<Pair<String, VariableData>>> fill(List<Function> functions, List<Function> consts, ImportsData imports) {
        List<Pair<String,VariableData>> list = new ArrayList<>();
        for(Function f : functions){
            var res = getFuncData(f, imports);
            if(res.hasError())
                return Results.makeError(res.getError());
            list.add(res.getValue());
        }
        for(Function f : consts){
            var res = getFuncData(f, imports);
            if(res.hasError())
                return Results.makeError(res.getError());
            list.add(res.getValue());
        }
        return Results.makeResult(list);
    }

    public static Result<Pair<String, VariableData>> getFuncData(Function func, ImportsData imports){
        var keyM = func.getKey(imports);
        if(keyM.hasError())
            return Results.makeError(keyM.getError());
        var VarData = getVarData(func);
        return Results.makeResult(new Pair<>(keyM.getValue(), VarData));
    }

    private static VariableData getVarData(Function func) {
        VariableData data = new VariableData();
        LaterInt scopeEnd = new LaterInt();
        for(Pair<String,String> x : func.getParams()){
            data.add(new Variable(x.getValue0(), x.getValue1(), 0, scopeEnd, data.getNextMemory()));
        }
        getVarDataHelper(func.getBody().getLines(), 0, data, scopeEnd);
        return data;
    }

    private static void getVarDataHelper(List<Line> lines, int lineNumber, VariableData data, LaterInt scopeEnd){
        for(Line l : lines){
            lineNumber = l.fill(lineNumber, data,scopeEnd);
        }
        scopeEnd.setNum(lineNumber);
    }


}
