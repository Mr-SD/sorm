package sorm; package object core {


import reflect.runtime.universe._


/**
 * A reference from a context type to its subfield. E.g., `context.field` or
 * `context.field.field` and so on.
 *
 * @tparam source A type of the context object.
 * @tparam target A type of the referred end-value.
 * @param contextType A type object of the context object.
 * @param subFieldSymbols A list of symbols representing the chain of subfields.
 *
 * @example
 *   For example, a reference `a.b.c` should be represented as follows:
 *   {{{
 *   SubRef
 *     [ <Type of `a`>, <Type of `c`> ]
 *     ( <rep of type of `a`>, List( <Symbol of field `b` of type of `a`>,
 *                                   <Symbol of field `c` of type of `a.b`> ) )
 *   }}}
 */
case class FieldRef
  [ source, target ]
  ( contextType : Type,
    subFieldSymbols : List[ Symbol ] )

sealed trait FieldRefs
  [ source, targets ]

object FieldRefs {
  case class FieldRefsValue
    [ source, targetsHead, targetsTail ]
    ( subRef : FieldRef[ source, targetsHead ],
      tail : FieldRefs[ source, targetsTail ] )
    extends FieldRefs[ source, (targetsHead, targetsTail) ]

  case class FieldRefsNil[ a ] extends FieldRefs[ a, Unit ]
}


// ----------------- the modern way

/**
 * A reference to a specific member from a root type.
 * Encodes the complete info at compile time.
 */
sealed trait TypePath
object TypePath {
  class Root extends TypePath
  class Member[ parent <: TypePath, index <: shapeless.Nat ] extends TypePath
}

// sealed trait KeyKind
// object KeyKind {
//   class Unique extends KeyKind
//   class NonUnique extends KeyKind
// }



}