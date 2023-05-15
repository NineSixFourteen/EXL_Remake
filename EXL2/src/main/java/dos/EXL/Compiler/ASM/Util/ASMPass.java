package dos.EXL.Compiler.ASM.Util;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ASMPass {

    // The object that each compileASM will accept 
    // Contains MethodVisitor to add instuctions to 
    // Contains info of each variable in a function aswell fields and import info inside of  
    MethodVisitor mv;
    ValueRecords records;

     
}
