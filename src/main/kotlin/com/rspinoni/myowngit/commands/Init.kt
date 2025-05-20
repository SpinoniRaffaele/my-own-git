package com.rspinoni.myowngit.commands

import com.rspinoni.myowngit.utils.RepositoryUtils
import picocli.CommandLine

@CommandLine.Command(
    name = "init",
    description = ["Initialize repository"]
)
class Init : Runnable {

    @CommandLine.Option(
        names = ["--path"],
        description = ["Path to the repository"],
        required = false,
        defaultValue = "."
    )
    var path: String = "."

    override fun run() {
        RepositoryUtils.createRepo(path)
    }
}