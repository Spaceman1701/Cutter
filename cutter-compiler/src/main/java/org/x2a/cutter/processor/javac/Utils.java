package org.x2a.cutter.processor.javac;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.util.List;
import org.x2a.cutter.processor.CutterCompileException;

import java.lang.annotation.Annotation;

class Utils {

    static boolean isAnnotatedWith(JCTree.JCMethodDecl methodDecl, Class<? extends Annotation> annotation) {
        return getAnnotation(methodDecl, annotation) != null;
    }

    static JCAnnotation getAnnotation(JCTree.JCMethodDecl methodDecl, Class<? extends Annotation> annotation) {
        List<JCAnnotation> annotationList = methodDecl.getModifiers().annotations;
        for (JCAnnotation jcAnnotation : annotationList) {
            if (jcAnnotation.attribute != null) {
                Type type = jcAnnotation.attribute.type;
                if (type.toString().equals(annotation.getName())) {
                    return jcAnnotation;
                }
            } else if (jcAnnotation.annotationType.toString().equals(annotation.getSimpleName())) {
                throw new CutterCompileException("Invalid Cut annotation location! (or another annotation has the simple name 'Cut', which is not currently supported");
            }
        }
        return null;
    }

    static JCTree.JCExpression getPointCut(JCAnnotation annotation) {
        JCTree.JCAssign assign = (JCTree.JCAssign) annotation.getArguments().get(0);
        JCTree.JCExpression clazzExp = resolveClassExpression((JCTree.JCFieldAccess) assign.rhs);
        return clazzExp;
    }

    private static JCTree.JCExpression resolveClassExpression(JCTree.JCFieldAccess clazzField) {
        return clazzField.selected;
    }
}
