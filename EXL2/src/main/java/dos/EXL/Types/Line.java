package dos.EXL.Types;

import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public interface Line {
    
    public void accept();

    public String makeString(int indent);

    public Maybe<MyError> validate(ValueRecords records);

    public void toASM(ASMPass pass);
}
