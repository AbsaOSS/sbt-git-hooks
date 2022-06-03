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

import java.io.{File, FileInputStream}
import java.security.{DigestInputStream, MessageDigest}

case class HookFile(name: String, file: File, checksum: String)

object HookFile {
  def apply(file: File): HookFile = {
    HookFile(file.getName, file, computeHash(file))
  }

  private def computeHash(file: File): String = {
    val buffer = new Array[Byte](8192)
    val md5 = MessageDigest.getInstance("MD5")

    val dis = new DigestInputStream(new FileInputStream(file), md5)
    try { while (dis.read(buffer) != -1) { } } finally { dis.close() }

    md5.digest.map("%02x".format(_)).mkString
  }
}
