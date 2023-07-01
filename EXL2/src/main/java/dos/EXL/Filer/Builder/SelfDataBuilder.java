package dos.EXL.Filer.Builder;

import dos.EXL.Filer.Program.SelfData;
import dos.EXL.Filer.Program.Function.FunctionData;

public class SelfDataBuilder {

    private SelfData self; 

    public SelfDataBuilder(String name){
        self = new SelfData(name);
    }

    public SelfDataBuilder addFunction(FunctionData fd){
        self.addFunction(fd.getName(),fd.getDesc(), fd.getType(), fd.getTags());
        return this;
    }

    public SelfData build(){
        return self;
    }
    
}
