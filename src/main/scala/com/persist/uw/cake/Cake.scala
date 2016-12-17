package com.persist.uw.cake

trait Util {
  val one = 1
}

trait A {
  util: Util =>
  def incr(i: Int) = i + one
}

class App {
  self: A with DB =>

  def double(i: Int) = 2 * incr(i)

  def run(s: String): Int = {
    val i = get(s)
    put(s, double(i))
    get(s)
  }
  // Can't

  // one
}

trait DB {
  def get(s: String): Int

  def put(s: String, i: Int)
}

trait MapDB extends DB {
  var map = Map[String,Int]("a"->50,"b"->99)

  def get(s: String) = map.getOrElse(s, 0)

  def put(s: String, i: Int): Unit = {
    map = map + (s -> i)
  }
}

trait TestDB extends DB {
  def get(s: String) = 50

  def put(s: String, i: Int) {}
}

object Cake {
  def main(args: Array[String]): Unit = {
    val kind = args.toSeq.headOption.getOrElse("")
    // Dependency injection for DB
    trait au extends A with Util
    val app = if (kind != "test") {
      new App with au with MapDB
    } else {
      new App with au with TestDB
    }
    println("a=" + app.run("a"))
    println("b=" + app.run("b"))
    println("c=" + app.run("c"))
  }
}
