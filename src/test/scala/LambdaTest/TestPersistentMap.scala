package LambdaTest

import com.fortysevendeg.lambdatest._
import java.io.File
import org.mapdb.DB.HashMapMaker
import scala.collection.mutable
import scala.collection.JavaConverters._
import org.mapdb._

// TODO Write PersistentMap.scala in
// src/main/scala/com/persist/uw/examples/PersistentMap.scala

// TODO fill in act in body
// Use LambdaTest to check open/close
// file creation, ...
// Use ScalaCheck to compare scala HashMap
// with your new persistent HashMap

class TestPersistentMap extends LambdaTest {
  def act = {
    test("Persistent Map") {
      assert(false,"NYI")
    }
  }
}

object TestPersistentMap {
  def main(args:Array[String]):Unit = {
    run("PersistentMap", new TestPersistentMap)
  }
}
