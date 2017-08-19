package org.x2a.cutter.processor.javac;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;

public class MethodTranslator extends TreeTranslator {

    @Override
    public void visitMethodDef(JCTree.JCMethodDecl methodDec) {
        super.visitMethodDef(methodDec);
        System.out.println("visit method called!");
        result = methodDec;
    }
}
