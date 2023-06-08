package dos.Util.Data.Builder;

import dos.Util.Data.FunctionData;
import dos.Util.Data.SelfData;

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
