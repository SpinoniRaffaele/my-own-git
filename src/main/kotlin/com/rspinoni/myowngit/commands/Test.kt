package com.rspinoni.myowngit.commands

import picocli.CommandLine

@CommandLine.Command(
    name = "test",
    description = ["Test command"]
)
class Test : Runnable {
    override fun run() {
        println("test works")
    }
}