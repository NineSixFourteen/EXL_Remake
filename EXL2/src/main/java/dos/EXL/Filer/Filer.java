package dos.EXL.Filer;

import dos.EXL.Filer.Program.ProgramData;
import dos.EXL.Types.Program;
import dos.Util.Result;
import dos.Util.Results;

public class Filer {

    public static Result<ProgramData> fill(Program prog){
        var imports = ImportFiler.fill(prog.getImports());
        var selfD = SelfFiler.fill(prog.getName(),prog.getFields(), prog.getFunctions(), imports.getValue());
        var methodInfo = VarDataFiler.fill(prog.getFunctions(), prog.getConstructors(), imports.getValue());
        return Results.makeResult(new ProgramData(imports.getValue(), selfD.getValue(), methodInfo.getValue()));
    }
    
}
