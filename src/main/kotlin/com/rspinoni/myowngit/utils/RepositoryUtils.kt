package com.rspinoni.myowngit.utils

import com.rspinoni.myowngit.model.Repository
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

class RepositoryUtils {
    companion object {
        private fun repoDir(repo: Repository, vararg path: String, mkdir: Boolean = false): String? {
            val path = Paths.get(repo.gitdir, *path)
            if (path.exists()) {
                if (path.isDirectory()) return path.toString()
                else throw IllegalArgumentException("The path $path is not a directory")
            }

            if (mkdir) {
                path.toFile().mkdirs()
                return path.toString()
            } else {
                return null
            }
        }

        private fun repoFile(repo: Repository, vararg path: String, mkdir: Boolean = false): File {
            if (repoDir(repo, *path.dropLast(1).toTypedArray(), mkdir = mkdir) != null) {
                return Paths.get(repo.gitdir, *path).toFile()
            } else {
                throw IllegalArgumentException("The path ${Paths.get(repo.gitdir, *path)} is not a file")
            }
        }

        fun createRepo(path: String) {
            val repo = Repository(path, true)

            val path = Path(repo.worktree)
            if (path.exists()) {
                if (!(path.isDirectory())) throw IllegalArgumentException("The path $path is not a directory")
                if (Path(repo.gitdir).exists()) throw IllegalArgumentException("The path $path is already a git repository")
            } else {
                path.toFile().mkdirs()
            }

            repoDir(repo, "branches", mkdir = true)
            repoDir(repo, "objects", mkdir = true)
            repoDir(repo, "refs", "tags", mkdir = true)
            repoDir(repo, "refs", "heads", mkdir = true)

            val description = repoFile(repo, "description")
            description.createNewFile()
            description.writeText("Unnamed repository; edit this file 'description' to name the repository.\n")

            val head = repoFile(repo, "HEAD")
            head.createNewFile()
            head.writeText("ref: refs/heads/master\n")

            val config = repoFile(repo, "config")
            config.createNewFile()
            config.writeText("[core]\n\trepositoryformatversion = 0\n\tfilemode = false\n\tbare = false\n")
        }

        fun findRepo(path: String = "."): Repository {
            val objPath = Path(path)
            if (objPath.isDirectory()) {
                return Repository(path)
            }
            val parent = objPath.parent
            if (parent == objPath) {
                throw IllegalArgumentException("No git repository found in $path")
            } else {
                return findRepo(parent.toString())
            }
        }
    }
}