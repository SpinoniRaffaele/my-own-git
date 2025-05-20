package com.rspinoni.myowngit

import com.rspinoni.myowngit.commands.Test
import io.quarkus.picocli.runtime.annotations.TopCommand
import picocli.CommandLine

@TopCommand
@CommandLine.Command(subcommands = [Test::class])
class GitMainCommand {}