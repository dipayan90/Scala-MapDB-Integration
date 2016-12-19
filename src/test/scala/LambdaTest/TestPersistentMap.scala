package LambdaTest

import com.fortysevendeg.lambdatest._
import java.io.File
import java.nio.file.{Files, Paths}

import org.scalacheck.Arbitrary.arbitrary
import com.persist.uw.examples.PersistentMap
import org.scalacheck.Prop._
import org.mapdb.DB.HashMapMaker

import scala.collection.mutable
import scala.collection.JavaConverters._
import org.mapdb._
import org.scalacheck.Gen

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
      assert(true, "NYI")
    } +
      test("File Open and Exists") {
        val map = new PersistentMap[String, Int]
        map.put("three", 3)
        map.put("four", 4)
        assert(Files.exists(Paths.get("file.db")))
      } +
      test("persistent input using put method, and produces correct output using get method") {
        val map = new PersistentMap[String, Int]
        map.put("three", 3)
        map.put("four", 4)
        assertEq(3, map.get("three").getOrElse(0), "got first object from map")
        assertEq(4, map.get("four").getOrElse(0), "got second object from map")
      } +
      test("persists input using put method, removes using remove method and produces correct output using get method") {
        val map = new PersistentMap[String, Int]
        map.put("three", 3)
        map.put("four", 4)
        map.put("five", 5)
        assertEq(4, map.get("four").getOrElse(0), "got object from the map, removing object now")
        map.remove("four")
        assert(!map.contains("four"), "key in hash-map doesn't exist anymore")
      }
  } +
    test("ScalaCheck") {
      test("persistent input using put method, and produces correct output using get method") {
        assertSC() {
          forAll {
            (a: String, b: Int) =>
              val map = new PersistentMap[String, Int]
              val scalaHashMap = new mutable.HashMap[String, Int]
              map.put(a, b)
              scalaHashMap.put(a, b)
              map.get(a) == scalaHashMap.get(a)
          }
        }
      } +
        test("persists input using put method, removes using remove method and produces correct output using get method") {
          assertSC() {
            forAll {
              (a: String, b: Int) =>
                val map = new PersistentMap[String, Int]
                val scalaHashMap = new mutable.HashMap[String, Int]
                map.put(a, b)
                scalaHashMap.put(a, b)
                map.remove(a) == scalaHashMap.remove(a)
            }
          }
        }
    }
}

object TestPersistentMap {
  def main(args: Array[String]): Unit = {
    run("PersistentMap", new TestPersistentMap)
  }
}
