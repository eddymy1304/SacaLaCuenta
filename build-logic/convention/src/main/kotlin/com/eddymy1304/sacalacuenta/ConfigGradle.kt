package com.eddymy1304.sacalacuenta

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependencyAnnotation: Any) {
    add("implementation", dependencyAnnotation)
}

fun DependencyHandler.testImplementation(dependencyAnnotation: Any) {
    add("testImplementation", dependencyAnnotation)
}

fun DependencyHandler.androidTestImplementation(dependencyAnnotation: Any) {
    add("androidTestImplementation", dependencyAnnotation)
}

fun DependencyHandler.ksp(dependencyAnnotation: Any) {
    add("ksp", dependencyAnnotation)
}