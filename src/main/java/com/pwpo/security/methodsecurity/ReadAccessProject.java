package com.pwpo.security.methodsecurity;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("(hasRole('USER') && @projectPermissionEvaluator.hasAccessToProject(#id)) or hasRole('MANAGER')")
public @interface ReadAccessProject {
}
