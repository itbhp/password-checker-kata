package it.twinsbrain.dojos

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class LibraryTest {
    @Test
    fun testSomeLibraryMethod() {
        val classUnderTest = Library()
        assertTrue(classUnderTest.someLibraryMethod(), "someLibraryMethod should return 'true'")
    }
}
