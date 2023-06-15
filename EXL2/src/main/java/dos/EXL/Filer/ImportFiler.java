package dos.EXL.Filer;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Filer.Imports.ImportsData;
import dos.Util.Result;
import dos.Util.Results;

public class ImportFiler {
    
    public static Result<ImportsData> fill(List<Pair<String,String>> importPaths){
        return Results.makeResult(new ImportsData());
    }

}
