package com.github.vasiliz.vkclient.base.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.PARAMETER)
@Retention(RUNTIME)
public @interface Query {

    String value();

}
