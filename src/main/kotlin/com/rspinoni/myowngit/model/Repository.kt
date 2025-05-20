package com.rspinoni.myowngit.model

import org.ini4j.Ini
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

class Repository {
    var worktree: String = ""
    var gitdir: String = ""
    var config: Ini? = null

    constructor(path: String, force: Boolean = false) {
        this.worktree = path
        this.gitdir = "$path/.git"

        if (!(force || Path(this.gitdir).isDirectory())) {
            throw IllegalArgumentException("The path $path is not a valid git repository")
        }

        val configPath = Path("$gitdir/config")

        if (configPath.exists()) {
            this.config = Ini(configPath.toFile())
        } else if (!force) {
            throw IllegalArgumentException("The path $path is not a valid git repository")
        }

        if (!force) {
            val version = this.config?.get("core")?.get("repositoryformatversion")?.toIntOrNull() ?: 0
            if (version != 0) {
                throw IllegalArgumentException("Unsupported repository format version: $version")
            }
        }
    }
}