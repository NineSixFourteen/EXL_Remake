package dos.Util.InfoClasses.Builder;

import dos.Util.InfoClasses.ImportsData;
import dos.Util.InfoClasses.ValueRecords;

public class ValueRecordsBuilder {
    
    private ValueRecords records;

    public ValueRecordsBuilder(){
        records = new ValueRecords();
    }

    public ValueRecordsBuilder addVar(String name, String type){
        records.addVariable(name, type);
        return this;
    }

    public ValueRecordsBuilder addFunction(String name, String type){
        records.addFunction(name, type);
        return this;
    }

    public ValueRecordsBuilder addField(String name, String type){
        records.addField(name, type);
        return this;
    }

    public ValueRecordsBuilder addImports(ImportsData data){
        records.addImports(data);
        return this;
    }

    public ValueRecords build(){
        return records;
    }



}
