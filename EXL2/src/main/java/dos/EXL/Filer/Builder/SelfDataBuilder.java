package dos.EXL.Filer.Builder;

import dos.EXL.Filer.Program.SelfData;
import dos.EXL.Filer.Program.Function.FunctionData;

public class SelfDataBuilder {

    private SelfData self; 

    public SelfDataBuilder(){
        self = new SelfData();
    }

    public SelfDataBuilder addFunction(FunctionData fd){
        self.addFunction(fd.getName(),fd.getDesc(), fd.getType());
        return this;
    }

    public SelfData build(){
        return self;
    }
    
}
