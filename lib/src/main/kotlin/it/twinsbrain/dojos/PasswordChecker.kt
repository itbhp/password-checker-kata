package it.twinsbrain.dojos

class PasswordChecker {
  fun verify(password: String): Boolean {
    return LengthConstraint.apply(password)
  }
}

interface Constraint {
  fun apply(password: String): Boolean
}

object LengthConstraint : Constraint{
  override fun apply(password: String): Boolean = password.length >= 7
}
