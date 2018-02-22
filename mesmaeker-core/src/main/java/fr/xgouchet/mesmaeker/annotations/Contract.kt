package fr.xgouchet.mesmaeker.annotations

import java.lang.annotation.Retention as JVMRetention

/**
 * @author Xavier F. Gouchet
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Contract
