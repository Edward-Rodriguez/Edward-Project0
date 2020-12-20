package scala
import org.bson.types.ObjectId

case class Pokemon(
    _id: Int,
    pokedexNumber: Int,
    name: String,
    hp: Int,
    attack: Int,
    defense: Int,
    specialAttack: Int,
    specialDefense: Int,
    speed: Int,
    type1: String,
    type2: String
)

object Pokemon {
  def apply(
      id: Int,
      pokedexNumber: Int,
      name: String,
      hp: Int,
      attack: Int,
      defense: Int,
      specialAttack: Int,
      specialDefense: Int,
      speed: Int,
      type1: String,
      type2: String
  ): Pokemon = new Pokemon(
    id: Int,
    pokedexNumber: Int,
    name: String,
    hp: Int,
    attack: Int,
    defense: Int,
    specialAttack: Int,
    specialDefense: Int,
    speed: Int,
    type1: String,
    type2: String
  )
}
