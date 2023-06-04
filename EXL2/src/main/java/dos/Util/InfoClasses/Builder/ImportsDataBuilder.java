package dos.Util.InfoClasses.Builder;

import java.util.List;

import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.ClassData;
import dos.Util.InfoClasses.ImportsData;

public class ImportsDataBuilder {

    private ImportsData data;

    public ImportsDataBuilder(){
        data = new ImportsData();
    }

    public ImportsDataBuilder addImports(String shortName, String longName, ClassData cd){
        data.addImport(shortName, longName, cd);
        return this;
    }

    public Result<ImportsData> validate(){
        List<String> names = data.getImportPaths().stream().map(x -> x.getValue0()).toList();
        for(String s : names){
            boolean b = false;
            for(String t : names){
                if(s.equals(t)){
                    if(b){
                        return Results.makeError(ErrorFactory.makeParser("Duplicate names used for importing " + s, 0));
                    }
                    b = true;
                }
            }
        }
        return Results.makeResult(data);
    } 

    public ImportsData build(){
        return data;
    }
}
