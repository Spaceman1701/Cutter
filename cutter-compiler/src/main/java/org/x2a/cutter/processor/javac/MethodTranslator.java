package org.x2a.cutter.processor.javac;

import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.SharedNameTable;

import java.util.ArrayList;

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
                   Name oldName = methodDecl.name;
                   methodDecl.name = elements.getName("AMethod2");


                   JCTree.JCMethodDecl newMethod = getNewMethodDeclaration(methodDecl.mods,
                           oldName, methodDecl.restype, methodDecl.typarams, methodDecl.params, methodDecl.thrown, methodDecl.body, methodDecl.defaultValue);
                   classDec.defs = classDec.defs.append(newMethod);
               }
            }
        }
        result = classDec;
    }

    private JCTree.JCMethodDecl getNewMethodDeclaration(JCTree.JCModifiers mods,
                                                        Name name,
                                                        JCTree.JCExpression returnType,
                                                        List<JCTree.JCTypeParameter> parameterTypes,
                                                        List<JCTree.JCVariableDecl> params,
                                                        List<JCTree.JCExpression> thrown,
                                                        JCTree.JCBlock body,
                                                        JCTree.JCExpression defaultValue) {
        return treeMaker.MethodDef(mods, name, returnType, parameterTypes, params, thrown, body, defaultValue);
    }
}
