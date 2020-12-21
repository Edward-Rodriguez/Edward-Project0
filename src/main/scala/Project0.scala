package scala

import org.mongodb.scala._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
object Project0 extends App {
  val t1 = System.nanoTime

  val pokeAPI = PokeUtility
  val pokemonList = Future {
    for (pokedexNumber <- 1 to 898) yield pokeAPI.getPokemon(pokedexNumber)
  }

  val mongoClient = MongoClient()
  val pokemonDao = new PokemonDao(mongoClient)

  pokemonList.onComplete {
    case Success(value) => {
      pokemonDao.insertCollectionIntoDatabase("Pokemon", value)
    }
    case Failure(ex) => println(ex)
  }

  def getTopAttackers(quantity: Int) {
    val size = pokemonList.map(_.size)
    size.onComplete {
      case Failure(ex) => println(ex)
      case Success(value) => {
        pokemonList
          .map(
            _.sortBy(_.attack)(Ordering[Int].reverse).dropRight(value - 20)
          )
          .onComplete {
            case Failure(ex) => println(ex)
            case Success(result) =>
              pokemonDao.insertCollectionIntoDatabase("TopAttackers", result)
          }
      }
    }
  }

  getTopAttackers(20)

  val duration = (System.nanoTime - t1) / 1e9d
  println(s"Time it took to run: $duration secs")

}
