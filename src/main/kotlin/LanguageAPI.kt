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
                val obj = GSON.fromJson(r, JsonObject::class.java) ?: JsonObject()
                obj.addProperty(key, value)

                BufferedWriter(FileWriter(translationsFile, ENCODING)).use { w ->
                    GSON.toJson(obj, w)
                }
            }
        }

        fun unregisterTranslation(key: String, locale: Locale) {
            val translationsFile = File(TRANSLATIONS_DIR, "${locale.language}.json")

            BufferedReader(FileReader(translationsFile)).use { r ->
                val obj = GSON.fromJson(r, JsonObject::class.java)?.asJsonObject ?: JsonObject()

                if (obj.isEmpty || !obj.has(key)) {
                    throw NoSuchElementException("No key $key in ${locale.language}.json")
                }

                obj.remove(key)

                BufferedWriter(FileWriter(translationsFile)).use { w ->
                    GSON.toJson(obj, w)
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