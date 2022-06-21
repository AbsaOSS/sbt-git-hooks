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

import sbt.{CopyOptions, IO, Logger}

import java.io.File

object GitHookFiles {
  private val gitHooksFolder = ".git/hooks"

  private def getListOfFiles(dir: File):List[File] = {
    val listOfAllFiles = if (dir.exists && dir.isDirectory) {
      dir.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }

    listOfAllFiles.filterNot(f => f.toString.endsWith(".sample"))
  }

  private def getMapOfHookFiles(directory: File): Map[String, HookFile] = {
    getListOfFiles(directory).map { f => f.getName -> HookFile(f)}.toMap
  }

  private def validatePath(source: File, target: File)(implicit logger: Logger): Unit = {
    if (!source.exists()) throw new Exception(s"Source path '$source' does not exist")
    if (!source.isDirectory) throw new Exception(s"Source path '$source' is not a directory")

    if(!target.exists()) {
      logger.info(s"Git hooks folder does not exist. Creating $gitHooksFolder.")
      target.mkdir()
    }
    if(!target.isDirectory) throw new Exception(s"$gitHooksFolder is not a directory")
  }

  private[plugins] def check(rootFile: File, hooksDir: String, overwrite: Boolean)(implicit logger: Logger): Seq[String] = {
    logger.info(s"Checking Git Hooks in source vs .git/hooks. Overwrite is $overwrite")
    val hooksTargetPath = new File(rootFile, gitHooksFolder)
    val hooksSourcePath = new File(rootFile, hooksDir)

    validatePath(hooksSourcePath, hooksTargetPath)

    val targetFiles: Map[String, HookFile] = getMapOfHookFiles(hooksTargetPath)
    val sourceFiles: Map[String, HookFile] = getMapOfHookFiles(hooksSourcePath)

    val filesToCopy = sourceFiles.foldLeft(Map.empty[String, String]) {
      case (acc, (name, sourceHookFile)) => targetFiles.get(name) match {
        case Some(hookFile) =>
          if (hookFile.checksum != sourceHookFile.checksum && overwrite)
            acc + (name -> "File exists but the checksums are not equal with source")
          else acc
        case None => acc + (name -> "File does not exist")
      }
    }

    for((name, reason) <- filesToCopy) {
      logger.info(s"Git Hook $name -> $reason")
    }

    filesToCopy.keys.toSeq
  }

  private[plugins] def sync(rootFile: File, hooksDir: String, overwrite: Boolean)(implicit logger: Logger): Unit = {
    logger.info(s"Syncing Git Hooks in source with .git/hooks. Overwrite is $overwrite")
    val hooksTargetPath = new File(rootFile, gitHooksFolder)
    val hooksSourcePath = new File(rootFile, hooksDir)

    validatePath(hooksSourcePath, hooksTargetPath)

    val targetFiles: Map[String, HookFile] = getMapOfHookFiles(hooksTargetPath)
    val sourceFiles: Map[String, HookFile] = getMapOfHookFiles(hooksSourcePath)

    sourceFiles.foreach {
      case (name, sourceHookFile) => targetFiles.get(name) match {
        case Some(hook) if hook.checksum != sourceHookFile.checksum && overwrite =>
          logger.info(s"Overwriting hook $name")
          IO.copyFile(sourceHookFile.file, hook.file, CopyOptions().withOverwrite(overwrite))
        case None =>
          logger.info(s"Copying file $name")
          IO.copyFile(sourceHookFile.file, new File(hooksTargetPath, name))
        case _ =>
      }
    }
  }
}
