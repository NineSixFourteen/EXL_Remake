package dos.Util.InfoClasses.Builder;

import dos.Util.InfoClasses.FunctionData;
import dos.Util.InfoClasses.SelfData;

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
