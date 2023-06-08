package dos.Util.Data.Builder;

import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Data.ClassData;
import dos.Util.Data.FunctionData;

public class ClassDataBuilder {

    private ClassData cd; 


    public ClassDataBuilder(){
        cd = new ClassData();
    }

    public ClassDataBuilder addFunction(String name, FunctionData fd){
        cd.addFunc(name, fd);
        return this;
    }

    public ClassDataBuilder addField(String name, String type){
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
