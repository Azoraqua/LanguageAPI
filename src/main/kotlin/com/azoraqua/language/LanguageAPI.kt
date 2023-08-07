@file:JvmName("LanguageAPI")
package com.azoraqua.language

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.*


class LanguageAPI private constructor() {
    companion object {
        // Constants --------------------------------------------------------
        private val DEFAULT_TRANSLATIONS_DIR = File("translations")
        private val DEFAULT_ENCODING = StandardCharsets.UTF_8
        private val GSON = Gson()
        // End of constants -------------------------------------------------

        private var translationsFolder: File = DEFAULT_TRANSLATIONS_DIR
        private var encoding: Charset = DEFAULT_ENCODING

        fun setTranslationsFolder(path: Path) {
            translationsFolder = path.toFile()
        }

        fun setEncoding(charset: Charset) {
            encoding = charset
        }

        fun registerTranslation(key: String, value: String, locale: Locale) {
            if (!translationsFolder.exists()) {
                translationsFolder.mkdirs()
            }

            val translationsFile = File(translationsFolder, "${locale.language}.json")

            if (!translationsFile.exists()) {
                translationsFile.createNewFile()
            }

            // Note: This can affect performance when working with large files.
            BufferedReader(FileReader(translationsFile, encoding)).use { r ->
                val obj = GSON.fromJson(r, JsonObject::class.java) ?: JsonObject()
                obj.addProperty(key, value)

                BufferedWriter(FileWriter(translationsFile, encoding)).use { w ->
                    GSON.toJson(obj, w)
                }
            }
        }

        fun unregisterTranslation(key: String, locale: Locale) {
            val translationsFile = File(translationsFolder, "${locale.language}.json")

            BufferedReader(FileReader(translationsFile, encoding)).use { r ->
                val obj = GSON.fromJson(r, JsonObject::class.java)?.asJsonObject ?: JsonObject()

                if (obj.isEmpty || !obj.has(key)) {
                    throw NoSuchElementException("No key $key in ${locale.language}.json")
                }

                obj.remove(key)

                BufferedWriter(FileWriter(translationsFile, encoding)).use { w ->
                    GSON.toJson(obj, w)
                }
            }
        }

        fun getTranslation(key: String, locale: Locale = Locale.getDefault()): String {
            // Note: This can affect performance when working with large files.
            return BufferedReader(FileReader(File(translationsFolder, "${locale.language}.json"), encoding)).use { r ->
                return@use GSON.fromJson(r, JsonObject::class.java).get(key)?.asString ?: key
            }
        }
    }
}