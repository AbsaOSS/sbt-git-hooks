package za.co.absa.sbt.plugins

import org.scalatest.funsuite.AnyFunSuite
import java.io.File

class HookFileSuite extends AnyFunSuite {

  test("Create a HookFile") {
    val expected = HookFile(
      "some-hook-file1",
      new File("/Users/absz189/Development/sbt-git-hooks/target/scala-2.12/sbt-1.0/test-classes/some-hook-file1"),
      "33ba382849e090bdde9fa9e994a5842f")
    val file = new File(this.getClass.getResource("/some-hook-file1").getFile)
    val hookFile = HookFile(file)
    assert(expected == hookFile)
  }
}
