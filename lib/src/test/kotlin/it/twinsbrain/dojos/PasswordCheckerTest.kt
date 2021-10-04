package it.twinsbrain.dojos

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class PasswordCheckerTest {
    private val underTest = PasswordChecker()
    @Test
    fun `it should have at least seven chars`() {
        val failMessage = "password should have minimum 7 chars"
        assertFalse(underTest.verify("123"), failMessage)
        assertTrue(underTest.verify("1234567"), failMessage)
        assertTrue(underTest.verify("123456789"), failMessage)
    }
}
