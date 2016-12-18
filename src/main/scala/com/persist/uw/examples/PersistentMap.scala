package com.persist.uw.examples

import java.io.File
import scala.reflect.runtime.universe._


import scala.collection.mutable
import scala.collection.JavaConverters._
import org.mapdb._

// TODO Your code for persistent map should go here.
class PersistentMap[K,V](implicit keyTag:TypeTag[K],valueTag:TypeTag[V]) extends mutable.Map[K,V] {

  val db = DBMaker
          .fileDB("file.db")
          .fileLockDisable()
          .checksumHeaderBypass()
          .closeOnJvmShutdown()
          .make()

  val map: HTreeMap[K,V] = (keyTag.tpe,valueTag.tpe) match {
    case (x,y) if x =:= typeOf[String] && y =:= typeOf[String] => db.hashMap[String,String]("stringStringHashMap",Serializer.STRING,Serializer.STRING).make().asInstanceOf[HTreeMap[K,V]]
    case (x,y) if x =:= typeOf[String] && y =:= typeOf[Int] => db.hashMap("stringIntegerHashMap",Serializer.STRING,Serializer.INTEGER).make().asInstanceOf[HTreeMap[K,V]]
    case (x,y) if x =:= typeOf[Int] && y =:= typeOf[String] => db.hashMap("integerStringHashMap",Serializer.INTEGER,Serializer.STRING).make().asInstanceOf[HTreeMap[K,V]]
    case (x,y) if x =:= typeOf[Int] && y =:= typeOf[Int] => db.hashMap("integerIntegerHashMap",Serializer.INTEGER,Serializer.INTEGER).make().asInstanceOf[HTreeMap[K,V]]
  }

  override def +=(kv: (K, V)): this.type = {
    map.put(kv._1,kv._2)
    this
  }

  override def -=(key: K): this.type = {
    map.remove(key)
    this
  }

  override def get(key: K): Option[V] = Option(map.get(key))

  override def iterator: Iterator[(K, V)] = {
    val set: Set[(K, V)] = map.entrySet().asScala.toSet.asInstanceOf[Set[(K,V)]]
    set.iterator
  }
}