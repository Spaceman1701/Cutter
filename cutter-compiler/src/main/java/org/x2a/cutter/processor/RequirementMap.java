package org.x2a.cutter.processor;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequirementMap {
    private Map<TypeMirror, List<TypeMirror>> requiredAnnotations;


    RequirementMap() {
        requiredAnnotations = new HashMap<>();
    }

    List<TypeMirror> getRequiredAnnotations(TypeMirror advice) {
        return requiredAnnotations.getOrDefault(advice, new ArrayList<>());
    }

    void put(TypeMirror advice, TypeMirror requirement) {
        if (!requiredAnnotations.containsKey(advice)) {
            requiredAnnotations.put(advice, new ArrayList<>());
        }
        requiredAnnotations.get(advice).add(requirement);
    }

    void putAll(TypeMirror advice, List<? extends TypeMirror> requirements) {
        for (TypeMirror m : requirements) {
            put(advice, m);
        }
    }

    boolean containsKey(TypeMirror advice) {
        return requiredAnnotations.containsKey(advice);
    }
}
