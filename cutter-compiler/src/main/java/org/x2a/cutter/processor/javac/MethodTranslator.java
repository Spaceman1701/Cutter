package org.x2a.cutter.processor.javac;

import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.SharedNameTable;

public class MethodTranslator extends TreeTranslator {

    private final TreeMaker treeMaker;
    private final JavacElements elements;

    public MethodTranslator(TreeMaker maker, JavacElements elements) {
        this.treeMaker = maker;
        this.elements = elements;
    }


    @Override
    public void visitClassDef(JCTree.JCClassDecl classDec) {
        super.visitClassDef(classDec);
        for (JCTree tree : classDec.getMembers()) {
            if (tree.getKind() == Tree.Kind.METHOD) {
               JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) tree;
               if (methodDecl.getName().toString().contains("AMethod")) {
                   methodDecl.name = elements.getName("AMethod2");
               }
            }
        }
        result = classDec;
    }
}
