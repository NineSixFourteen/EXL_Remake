package dos.EXL.Filer.Imports;

public class ImportInfo {

    private String shortName; // ShortName i.e. String 
    private String longName;  // LongName i.e. Java.Lang.String
    private ClassData data;   // The data about the class i.e. fields and functions data
    

    public ImportInfo(String sName,String lName, ClassData Data){
        shortName = sName;
        longName = lName;
        data = Data;
    }

    public ClassData getData() {
        return data;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean is(String name){
        return name.equals(shortName) || longName.equals(name);
    }
    
}
