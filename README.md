# sbt-git-hooks

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Release](https://github.com/AbsaOSS/sbt-git-hooks/actions/workflows/release.yml/badge.svg)](https://github.com/AbsaOSS/sbt-git-hooks/actions/workflows/release.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/za.co.absa.sbt/sbt-git-hooks_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/za.co.absa.sbt/sbt-git-hooks_2.12)

sbt-git-hooks is an [sbt](http://www.scala-sbt.org) plugin for maintaining git hooks as part of your code.

Git hooks originally reside in `.git/hooks` which is not versioned as part of the code, but it is something you
want to share with other contributors to your code. This plugin syncs git hooks from your project folder with the ones
in the `.git/hooks` folder. 

Sample files are ignored.

## Getting started

In order to add the sbt-git-hooks plugin to your build, add the following line to `project/plugins.sbt`:

``` sbt
addSbtPlugin("za.co.absa.sbt" % "sbt-git-hooks" % "X.Y.Z")
```

Then, optionally, in your `build.sbt` configure the following settings:

```sbt
overwriteGitHookFiles := true 
gitHookFilesLocation := "project/git_hooks"
```

- `overwriteGitHookFiles` setting allows you to overwrite the files already present in `.git/hooks`. Defaults to `false`
- `gitHookFilesLocation` setting allows you to set a different folder for source of hook files. This is relative to the project. Defaults to `project/git_hooks` 

### Syncing hook files

To run a sync use `sbt syncGitHooks`.

### Checking what files would get synced if any

To check which files would get synced and what would happen run `sbt getGitHooksToSync`

### Requirements

- Java 8 or higher
- sbt 1.3.0 or higher

## How to Release

Please see [this file](RELEASE.md) for more details.
