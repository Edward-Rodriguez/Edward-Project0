package scala

import java.util.HashSet
import org.mongodb.scala._
import scala.collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.bson.types.ObjectId;
import Pokemon._
import PokeUtility._
import scala.io.StdIn.readInt
import scala.util.Success
import scala.util.Failure

object Project0 extends App {
  val t1 = System.nanoTime
  val pokeAPI = PokeUtility
  val mongoClient = MongoClient()
  val pokemonDao = new PokemonDao(mongoClient)

  // val pokemonCollection: Set[Pokemon] = pokeAPI.getPokemonCollection(200)
  val pokemonList = Future {
    for (pokedexNumber <- 1 to 893) yield pokeAPI.getPokemon(pokedexNumber)
  }

  pokemonList.onComplete {
    case Success(value) => {
      pokemonDao.insertCollectionIntoDatabase("Attack", value)
    }
    case Failure(ex) => println(ex)
  }

  //Matches pokemon who's attack >= `minAttack`.
  // def findPokemonByAttackGT(minAttack: Int) {
  //   val res = pokemonList.foreach(_.filter(_.attack >= minAttack))
  // }
  // val results =
  //   findPokemonByAttackGT(50).toSeq.sortBy(_.attack)(
  //     Ordering[Int].reverse
  //   )
  // println(s"${highestAttack.name} = ${highestAttack.attack}")

  val duration = (System.nanoTime - t1) / 1e9d
  println(s"Time it took to run: $duration secs")

}
