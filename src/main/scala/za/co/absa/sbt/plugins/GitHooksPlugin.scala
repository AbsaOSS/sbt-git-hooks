/*
 * Copyright 2022 ABSA Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.absa.sbt.plugins

import sbt.*
import sbt.Keys.*


object GitHooksPlugin extends sbt.AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    val overwriteGitHookFiles = settingKey[Boolean]("Should hooks be overwritten if checksums don't match")
    val gitHookFilesLocation = settingKey[String]("Location of source git hook files")

    val getGitHooksToSync = taskKey[Seq[String]]("Checks the git hooks files are up to date")
    val syncGitHooks = taskKey[Unit]("Syncs the git hook files")
  }

  import autoImport.*

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    overwriteGitHookFiles := false,
    gitHookFilesLocation := "project/git_hooks",

    getGitHooksToSync := {
      GitHookFiles
        .check(baseDirectory.value, gitHookFilesLocation.value, overwriteGitHookFiles.value)(streams.value.log)
    },

    syncGitHooks := {
      GitHookFiles
        .sync(baseDirectory.value, gitHookFilesLocation.value, overwriteGitHookFiles.value)(streams.value.log)
    }
  )
}

