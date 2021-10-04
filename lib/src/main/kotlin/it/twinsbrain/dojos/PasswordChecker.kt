package it.twinsbrain.dojos

class PasswordChecker(private val constraint: Constraint) {
  fun verify(password: String): Boolean {
    return constraint.apply(password)
  }
}

interface Constraint {
  fun apply(password: String): Boolean
}

class ComposedConstraint(private vararg val constraint: Constraint) : Constraint {
  override fun apply(password: String): Boolean = constraint.all { it.apply(password) }
}

object LengthConstraint : Constraint {
  override fun apply(password: String): Boolean = password.length >= 7
}

object AlphaNumericConstraint : Constraint {
  override fun apply(password: String): Boolean = password.containsAlphaNumeric()

  private fun String.containsAlphaNumeric(): Boolean =
    this.any { it in 'A'..'Z' || it in 'a'..'z' || it in '0'..'9' }
}

object SpecialCharsConstraint : Constraint {
  override fun apply(password: String): Boolean = password.containsSpecialChars()

  private fun String.containsSpecialChars(): Boolean =
    this.any { it in "±§!@#$%^&*()_+{}[]:;\"',<.>?/|`~\\" }
}
