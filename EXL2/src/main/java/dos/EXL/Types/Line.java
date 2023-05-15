package dos.EXL.Types;

import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;

public interface Line {
    
    public void accept();

    public String makeString(int indent);

    public Maybe<Error> validate();

    public void toASM(ASMPass pass);
}
