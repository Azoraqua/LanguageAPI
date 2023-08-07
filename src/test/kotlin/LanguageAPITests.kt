
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*
import kotlin.test.assertEquals

class LanguageAPITests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup(): Unit = Unit

        @JvmStatic
        @AfterAll
        fun cleanup() = Unit
    }

    @Test
    fun test_registration() {
        LanguageAPI.registerTranslation("hello.world", "Hello World", Locale.ENGLISH)
    }

    @Test
    fun test_unregistration() {
        assertDoesNotThrow {
            LanguageAPI.unregisterTranslation("hello.world", Locale.ENGLISH)
        }
    }

    @Test
    fun test_retrieval() {
        assertEquals("Hello World", LanguageAPI.getTranslation("hello.world"))
    }
}