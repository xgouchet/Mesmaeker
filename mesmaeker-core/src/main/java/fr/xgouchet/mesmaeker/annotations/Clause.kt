package fr.xgouchet.mesmaeker.annotations

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention as JVMRetention

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Clause
