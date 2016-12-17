package com.persist.uw.examples

import java.io.File

import com.karasiq.mapdb.serialization.MapDbSerializer
import org.mapdb.DB.HashMapMaker

import scala.collection.mutable
import scala.collection.JavaConverters._
import org.mapdb._


// TODO Your code for persistent map should go here.
class PersistentMap[K,V] extends mutable.Map[K,V] {

  val db = DBMaker.memoryDB().make()
  val map: HTreeMap[K,V] = db.hashMap[K,V]("pHashMap",MapDbSerializer.orDefault[K],MapDbSerializer.orDefault[V]).make()

  override def +=(kv: (K, V)): PersistentMap.this.type = {
    map.put(kv._1,kv._2)
    map.asInstanceOf[this.type]
  }

  override def -=(key: K): PersistentMap.this.type = {
    map.remove(key)
    map.asInstanceOf[this.type]
  }

  override def get(key: K): Option[V] = Option(map.get(key))

  override def iterator: Iterator[(K, V)] = {
    map.entrySet().iterator().asInstanceOf[Iterator[(K,V)]]
  }
}