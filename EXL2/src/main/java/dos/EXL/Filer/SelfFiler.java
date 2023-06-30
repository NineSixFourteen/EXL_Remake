package dos.EXL.Filer;

import java.util.List;

import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.SelfData;
import dos.EXL.Types.Function;
import dos.EXL.Types.Lines.Field;
import dos.Util.Result;
import dos.Util.Results;

public class SelfFiler {


    public static Result<SelfData> fill(String name, List<Field> fields, List<Function> functions, ImportsData imports){
        SelfData self = new SelfData(name);
        for(Field field : fields){
            self.addField(field.getName(), field.getType());
        }
        for(Function func : functions){
            var des = func.getDesc(imports);
            if(des.hasError())
                return Results.makeError(des.getError());
            self.addFunction(func.getName(), des.getValue(),func.getType());
        }
        return Results.makeResult(self);
    }
    
}
