package dos.EXL.Filer.Builder;

import dos.EXL.Filer.Imports.ClassData;
import dos.EXL.Filer.Program.Function.FunctionData;
import dos.Util.Result;
import dos.Util.Results;

public class ClassDataBuilder {

    private ClassData cd; 


    public ClassDataBuilder(){
        cd = new ClassData();
    }

    public ClassDataBuilder addFunction(String name, FunctionData fd){
        cd.addFunc(name, fd);
        return this;
    }

    public ClassDataBuilder addField(String name,String type){
        cd.addField(name, type);
        return this;
    }

    public ClassDataBuilder addConstructor(FunctionData construct){
        cd.addConstructor(construct);
        return this;
    }
    
    public Result<ClassData> validate(){
        return Results.makeResult(cd);
    }

    public ClassData build(){
        return cd;
    }
    
}
