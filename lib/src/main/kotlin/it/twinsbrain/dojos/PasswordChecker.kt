package it.twinsbrain.dojos

class PasswordChecker {
  fun verify(password: String): Boolean {
    return password.length >= 7
  }
}
