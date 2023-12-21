package iem.fraunhofer.de

import java.io.File

fun main(args: Array<String>) {
    val path = args[0]
    println("Running analyzer for project at path $path")
    val analyzer = DependencyAnalyzer()
    analyzer.runAnalyzer(File(path))
}