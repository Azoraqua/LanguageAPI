# LanguageAPI ![](https://badgen.net/github/release/Azoraqua/LanguageAPI)
A simple library for introducing multiple languages into projects.  



## Installation:
**Note:** None of the dependencies below will work at the moment. Work in progress.

- Maven:
  ```xml
  <dependency>
    <groupId>com.azoraqua</groupId>
    <artifactId>LanguageAPI</artifactId>
    <version>{VERSION}</version>
  </dependency>
  ```
- Gradle (Groovy):
  ```groovy
  implementation 'com.azoraqua:LanguageAPI:{VERSION}'
  ```
- Gradle (Kotlin)
  ```kt
  implementation("com.azoraqua:LanguageAPI:{VERSION}")
  ```


## How to use:
- Registration:
  ```kt
  com.azoraqua.language.LanguageAPI.registerTranslation("hello.world", "Hello World", Locale.ENGLISH)
  ```
- Retrieval:
  ```kt
  // Optionally, provide a locale if you want the translation in a different language other than the system's default.
  com.azoraqua.language.LanguageAPI.getTranslation("hello.world")
  ```
