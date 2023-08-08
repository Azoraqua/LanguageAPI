@file:JvmName("LanguageAPI")

package com.azoraqua.language

import com.google.common.cache.CacheBuilder
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

internal data class Translation(val key: String, val value: String)

class LanguageAPI private constructor() {
    companion object {
        // Constants --------------------------------------------------------
        private val DEFAULT_TRANSLATIONS_DIR = File("translations")
        private val DEFAULT_ENCODING = StandardCharsets.UTF_8
        private const val DEFAULT_EXPIRATION = 3600L // Seconds
        private const val DEFAULT_MAX_CACHE_SIZE = 150L
        private val GSON = Gson()
        // End of constants -------------------------------------------------

        private var translationsFolder: File = DEFAULT_TRANSLATIONS_DIR
        private var encoding: Charset = DEFAULT_ENCODING
        private val cache = CacheBuilder.newBuilder().maximumSize(DEFAULT_MAX_CACHE_SIZE)
            .expireAfterAccess(DEFAULT_EXPIRATION, TimeUnit.SECONDS).build<Locale, MutableSet<Translation>>()

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
            BufferedReader(FileReader(translationsFile)).use { r ->
                val obj = GSON.fromJson(r, JsonObject::class.java) ?: JsonObject()
                obj.addProperty(key, value)

                BufferedWriter(FileWriter(translationsFile)).use { w ->
                    GSON.toJson(obj, w)
                }
            }
        }

        fun unregisterTranslation(key: String, locale: Locale) {
            val translationsFile = File(translationsFolder, "${locale.language}.json")

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
            return getOrLoad(key, locale) ?: key
        }

        private fun getOrLoad(key: String, locale: Locale = Locale.getDefault()): String? {
            return cache.get(locale, Callable {
                val translationsFile = File(translationsFolder, "${locale.language}.json")
                val translations = mutableSetOf<Translation>()

                BufferedReader(FileReader(translationsFile)).use { r ->
                    translations.addAll(GSON.fromJson(r, JsonObject::class.java).asMap().map {
                        Translation(it.key, it.value.asString)
                    })
                }

                return@Callable translations
            }).find { it.key == key }?.value
        }
    }
}