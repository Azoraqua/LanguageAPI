import org.junit.jupiter.api.*
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class LanguageAPITests {
    companion object {
        private val dataFolder = File("src/test/resources/translations")

        @JvmStatic
        @BeforeAll
        fun setup() {
            LanguageAPI.setTranslationsFolder(dataFolder.toPath())
            LanguageAPI.setEncoding(StandardCharsets.US_ASCII)
        }

        @JvmStatic
        @AfterAll
        fun cleanup() {
            dataFolder.listFiles()?.forEach { it.delete() }
            dataFolder.delete()

            LanguageAPI.setTranslationsFolder(File("translations").toPath())
            LanguageAPI.setEncoding(StandardCharsets.UTF_8)
        }
    }

    @Order(1)
    @Test
    fun test_registration() {
        LanguageAPI.registerTranslation("hello.world", "Hello World", Locale.ENGLISH)

        assertEquals("Hello World", LanguageAPI.getTranslation("hello.world"))
    }

    @Order(2)
    @Test
    fun test_unregistration() {
        assertDoesNotThrow {
            LanguageAPI.unregisterTranslation("hello.world", Locale.ENGLISH)
        }
    }
}