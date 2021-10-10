package it.twinsbrain.dojos

import arrow.core.NonEmptyList
import arrow.core.invalid
import arrow.core.valid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PasswordValidatorTest {

  @Test
  internal fun lengthValidation() {
    val validator = PasswordValidator(listOf(LengthValidator(7)))

    assertThat(validator.check("123456")).isEqualTo(nonEmptyList(TooFewChars).invalid())

    val password = "1234567"
    assertThat(validator.check(password)).isEqualTo(password.valid())
  }

  @Test
  internal fun noAlphanumericCharFoundValidation() {
    val validator = PasswordValidator(listOf(AlphaNumericValidator))

    assertThat(validator.check("){][;?")).isEqualTo(nonEmptyList(NoAlphanumericCharFound).invalid())

    val password = "1234567light"
    assertThat(validator.check(password)).isEqualTo(password.valid())
  }

  @Test
  internal fun noSpecialCharFoundValidation() {
    val validator = PasswordValidator(listOf(SpecialCharsValidator))

    assertThat(validator.check("212311")).isEqualTo(nonEmptyList(NoSpecialCharFound).invalid())

    val password = "){][;?"
    assertThat(validator.check(password)).isEqualTo(password.valid())
  }

  @Test
  internal fun acceptanceTest() {
    val validator = PasswordValidator(listOf(SpecialCharsValidator, AlphaNumericValidator, LengthValidator(7)))

    assertThat(validator.check("212a")).isEqualTo(nonEmptyList(NoSpecialCharFound, TooFewChars).invalid())

    val password = "123war){][;?"
    assertThat(validator.check(password)).isEqualTo(password.valid())
  }

  private fun nonEmptyList(vararg errors: ValidationError) = NonEmptyList.fromListUnsafe(errors.toList())
}