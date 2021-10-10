package it.twinsbrain.dojos

import arrow.core.*
import arrow.typeclasses.Semigroup

class PasswordValidator(private val validations: List<Rule>) {
  fun check(password: String): ValidatedNel<ValidationError, Password> = password.checkAll(validations)

  private fun String.checkAll(
    validations: List<Rule>
  ): ValidatedNel<ValidationError, Password> =
    validations
      .map { it.check(this) }
      .sequenceValidated(Semigroup.nonEmptyList())
      .map { it.first() }
}

typealias Password = String

interface Rule {
  fun check(password: String): ValidatedNel<ValidationError, Password>
}

sealed class ValidationError
object TooFewChars : ValidationError()
object NoAlphanumericCharFound : ValidationError()
object NoSpecialCharFound : ValidationError()

data class LengthValidator(val length: Int) : Rule {
  override fun check(password: String): ValidatedNel<ValidationError, Password> =
    if (password.length >= length) {
      Validated.validNel(password)
    } else {
      Validated.invalidNel(TooFewChars)
    }
}

object AlphaNumericValidator : Rule {
  private fun String.containsAlphaNumeric(): Boolean =
    this.any { it in 'A'..'Z' || it in 'a'..'z' || it in '0'..'9' }

  override fun check(password: String): ValidatedNel<ValidationError, Password> =
    if (password.containsAlphaNumeric()) {
      Validated.validNel(password)
    } else {
      Validated.invalidNel(NoAlphanumericCharFound)
    }
}

object SpecialCharsValidator : Rule {

  private fun String.containsSpecialChars(): Boolean =
    this.any { it in "±§!@#$%^&*()_+{}[]:;\"',<.>?/|`~\\" }

  override fun check(password: String): ValidatedNel<ValidationError, Password> =
    if (password.containsSpecialChars()) {
      Validated.validNel(password)
    } else {
      Validated.invalidNel(NoSpecialCharFound)
    }
}
