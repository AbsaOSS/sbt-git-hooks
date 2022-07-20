/*
 * Copyright 2022 ABSA Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

ThisBuild / sonatypeProfileName := "za.co.absa"

ThisBuild / organization := "za.co.absa.sbt"
ThisBuild / organizationName := "ABSA Group Limited"
ThisBuild / organizationHomepage := Some(url("https://www.absa.africa"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    browseUrl = url("https://github.com/AbsaOSS/sbt-git-hooks/tree/master"),
    connection = "scm:git:git://github.com/AbsaOSS/sbt-git-hooks.git",
    devConnection = "scm:git:ssh://github.com/AbsaOSS/sbt-git-hooks.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "Zejnilovic",
    name  = "Sasa Zejnilovic",
    email = "sasa.zejnilovic@absa.africa",
    url   = url("https://github.com/Zejnilovic")
  ),
  Developer(
    id    = "lsulak",
    name  = "Ladislav Sulak",
    email = "ladislav.sulak@absa.africa",
    url   = url("https://github.com/lsulak")
  )
)

ThisBuild / homepage := Some(url("https://github.com/AbsaOSS/sbt-git-hooks"))
ThisBuild / description := "sbt plugin to synchronize git hooks files with project files"
ThisBuild / startYear := Some(2022)
ThisBuild / licenses += "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt")
