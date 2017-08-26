package org.x2a.cutter.processor.javac;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.Constants;
import org.x2a.cutter.annotation.Cut;

public class MethodTranslator extends TreeTranslator {

    private final TreeFactory factory;

    public MethodTranslator(TreeFactory factory) {
        this.factory = factory;
    }


    @Override
    public void visitClassDef(JCTree.JCClassDecl classDec) {
        super.visitClassDef(classDec);
        for (JCTree tree : classDec.getMembers()) {
            if (tree.getKind() == Tree.Kind.METHOD) {
               JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) tree;
               if (Utils.isAnnotatedWith(methodDecl, Cut.class)) {
                   classDec.defs = classDec.defs.append(renameAndCreateWrapper(methodDecl));
               }
            }
        }
        result = classDec;
    }

    private JCMethodDecl renameAndCreateWrapper(JCMethodDecl methodDecl) {
        Name oldName = methodDecl.name;
        methodDecl.name = factory.getName(Constants.METHOD_WRAPPED_PREFIX + oldName.toString());
        PointCutCreator methodCreator = new PointCutCreator(factory, methodDecl, oldName, Utils.getAnnotation(methodDecl, Cut.class));
        JCMethodDecl newMethod = methodCreator.createMethod();
        methodDecl.mods.flags = Flags.PRIVATE | ((methodDecl.mods.flags & ~Flags.PUBLIC) & ~Flags.PROTECTED);
        return newMethod;
    }
}
