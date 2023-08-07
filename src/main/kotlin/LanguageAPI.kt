import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*

class LanguageAPI private constructor() {
    companion object {
        private val TRANSLATIONS_DIR = File("translations")
        private val GSON = Gson()
        private val ENCODING = StandardCharsets.UTF_8

        fun registerTranslation(key: String, value: String, locale: Locale) {
            if (!TRANSLATIONS_DIR.exists()) {
                TRANSLATIONS_DIR.mkdirs()
            }

            val translationsFile = File(TRANSLATIONS_DIR, "${locale.language}.json")

            if (!translationsFile.exists()) {
                translationsFile.createNewFile()
            }

            // Note: This can affect performance when working with large files.
            BufferedReader(FileReader(translationsFile, ENCODING)).use { r ->
                BufferedWriter(FileWriter(translationsFile, ENCODING)).use { w ->
                    GSON.toJson(GSON.fromJson(r, JsonObject::class.java).let {
                        it.addProperty(key, value)
                        return@let it
                    }, w)
                }
            }
        }

        fun getTranslation(key: String, locale: Locale = Locale.getDefault()): String {
            // Note: This can affect performance when working with large files.
            return BufferedReader(FileReader(File(TRANSLATIONS_DIR, "${locale.language}.json"))).use { r ->
                return@use GSON.fromJson(r, JsonObject::class.java).get(key)?.asString ?: key
            }
        }
    }
}