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
  private val rule = BooleanRule({ it.length >= length }, TooFewChars)
  override fun check(password: String): ValidatedNel<ValidationError, Password> = rule.check(password)
}

object AlphaNumericValidator : Rule {
  private val rule = BooleanRule({ it.containsAlphaNumeric() }, NoAlphanumericCharFound)

  private fun String.containsAlphaNumeric(): Boolean =
    this.any { it in 'A'..'Z' || it in 'a'..'z' || it in '0'..'9' }

  override fun check(password: String): ValidatedNel<ValidationError, Password> = rule.check(password)
}

object SpecialCharsValidator : Rule {
  private val rule = BooleanRule({ it.containsSpecialChars() }, NoSpecialCharFound)

  private fun String.containsSpecialChars(): Boolean =
    this.any { it in "±§!@#$%^&*()_+{}[]:;\"',<.>?/|`~\\" }

  override fun check(password: String): ValidatedNel<ValidationError, Password> = rule.check(password)
}

data class BooleanRule(val isValid: Predicate<String>, val error: ValidationError) : Rule {
  override fun check(password: String): ValidatedNel<ValidationError, Password> {
    return if (isValid(password)) {
      password.valid()
    } else {
      Validated.invalidNel(error)
    }
  }
}
