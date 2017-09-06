package org.x2a.cutter.processor;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.annotation.RequiredAnnotations;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("org.x2a.cutter.annotation.RequiredAnnotations")
@SupportedSourceVersion(SourceVersion.RELEASE_0)
public class VerificationProcessor extends AbstractProcessor {

    private JavacProcessingEnvironment javacEnv;
    @Override
    public void init(ProcessingEnvironment env) {
        this.javacEnv = (JavacProcessingEnvironment) env;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if (!roundEnv.processingOver()) {
                RequirementMap requirementMap = buildRequirmentMap(roundEnv);
                verifyRequirements(requirementMap, roundEnv.getElementsAnnotatedWith(Cut.class));
            }
        } catch (RuntimeException e) {
            javacEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Cutter compile-time exception: " + e.getMessage());
            e.printStackTrace();
        }
        return true; //claims the required annotations annotation
    }

    private void verifyRequirements(RequirementMap requirementMap, Set<? extends Element> elements) {
        for (Element e : elements) {
            Cut cut = e.getAnnotation(Cut.class);
            TypeMirror advice = getTypeMirror(cut);
            List<? extends TypeMirror> requiredAnnotations = requirementMap.getRequiredAnnotations(advice);
            Set<TypeMirror> annotations = buildTypeMirrorSet(e.getAnnotationMirrors());
            for (TypeMirror required : requiredAnnotations) {
                if (!annotations.contains(required)) {
                    throw new CutterCompileException(e + " is missing required annotation: " +
                            required + " as required by " + advice);
                }
            }
        }
    }

    private TypeMirror getTypeMirror(Cut cutProxy) {
        try {
            return javacEnv.getElementUtils().getTypeElement(cutProxy.value().getCanonicalName()).asType();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
    }

    private Set<TypeMirror> buildTypeMirrorSet(List<? extends AnnotationMirror> annotationMirrors) {
        Set<TypeMirror> mirrors = new HashSet<>();
        for (AnnotationMirror am : annotationMirrors) {
            mirrors.add(am.getAnnotationType());
        }
        return mirrors;
    }

    private RequirementMap buildRequirmentMap(RoundEnvironment env) {
        RequirementMap map = new RequirementMap();
        Symbol.ClassSymbol adviceBaseType = javacEnv.getElementUtils().getTypeElement("org.x2a.cutter.cut.Advice");
        for (Element e : env.getElementsAnnotatedWith(RequiredAnnotations.class)) {
            if (e.getKind() != ElementKind.CLASS) {
                throw new CutterCompileException("@RequiredAnnotations must be placed on a class!");
            }
            TypeMirror typeMirror = e.asType();
            Symbol.ClassSymbol clazzSym = (Symbol.ClassSymbol) e;
            if (!clazzSym.isSubClass(adviceBaseType, Types.instance(javacEnv.getContext()))) {
                throw new CutterCompileException("@RequiredAnnotations must be placed on an Advice class");
            }
            RequiredAnnotations annotation = e.getAnnotation(RequiredAnnotations.class);
            map.putAll(typeMirror, getValue(annotation));
        }
        return map;
    }

    private List<? extends TypeMirror> getValue(RequiredAnnotations annotationProxy) {
        try {
            annotationProxy.value();
        } catch (MirroredTypesException e) {
            return e.getTypeMirrors();
        }
        return null;
    }
}
