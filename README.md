# LanguageAPI 
[![](https://jitpack.io/v/com.azoraqua/LanguageAPI.svg)](https://jitpack.io/#com.azoraqua/LanguageAPI)

A simple library for introducing multiple languages into projects.

## Installation:

- Maven:
  ```xml
  // Repository
  <repository>
	  <id>jitpack.io</id>
		<url>https://jitpack.io</url>
  </repository>

  // Dependency
  <dependency>
    <groupId>com.azoraqua</groupId>
    <artifactId>LanguageAPI</artifactId>
    <version>{VERSION}</version>
  </dependency>
  ```
- Gradle (Groovy):
  ```groovy
  // Repository
  maven { url 'https://jitpack.io' } 

  // Dependency
  implementation 'com.azoraqua:LanguageAPI:{VERSION}'
  ```
- Gradle (Kotlin):
  ```kt
  // Repository
  maven("https://jitpack.io")

  // Dependency
  implementation("com.azoraqua:LanguageAPI:{VERSION}")
  ```

## How to use:

- Registration:
  ```kt
  LanguageAPI.registerTranslation("hello.world", "Hello World", Locale.ENGLISH)
  ```
- Unregistration:  
  ```kt
  LanguageAPI.unregisterTranslation("hello.world", Locale.ENGLISH)
  ```
- Retrieval:
  ```kt
  // Optionally, provide a locale if you want the translation in a different language other than the system's default.
  LanguageAPI.getTranslation("hello.world")
  ```
