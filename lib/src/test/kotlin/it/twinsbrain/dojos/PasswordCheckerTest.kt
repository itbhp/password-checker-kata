package it.twinsbrain.dojos

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class PasswordCheckerTest {
    private val underTest = PasswordChecker()
    @Test
    fun `it should have at least seven chars`() {
        assertFalse(underTest.verify("123"), "password should have minimum 7 chars")
    }
}
