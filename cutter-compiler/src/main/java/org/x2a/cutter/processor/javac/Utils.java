package org.x2a.cutter.processor.javac;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.PointCut;

import java.lang.annotation.Annotation;

class Utils {

    static boolean isAnnotatedWith(JCTree.JCMethodDecl methodDecl, Class<? extends Annotation> annotation) {
        List<JCTree.JCAnnotation> annotationList = methodDecl.getModifiers().annotations;
        for (JCTree.JCAnnotation jcAnnotation : annotationList) {
            Type type = jcAnnotation.attribute.type;
            if (type.toString().equals(annotation.getName())) {
                return true;
            }
        }
        return false;
    }

}
