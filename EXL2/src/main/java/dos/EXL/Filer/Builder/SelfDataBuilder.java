package dos.EXL.Filer.Builder;

import dos.EXL.Filer.Program.SelfData;
import dos.EXL.Filer.Program.Function.FunctionData;

public class SelfDataBuilder {

    private SelfData self; 

    public SelfDataBuilder(){
        self = new SelfData();
    }

    public SelfDataBuilder addFunction(String name, FunctionData fd){
        self.addFunction(name,fd.getDesc());
        return this;
    }

    public SelfData build(){
        return self;
    }
    
}
