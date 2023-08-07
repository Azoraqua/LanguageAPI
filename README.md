# LanguageAPI | ![](https://badgen.net/github/release/Azoraqua/LanguageAPI)
A simple library for introducing multiple languages into projects.  



## Installation:
- Maven:
  ```xml
  <dependency>
    <groupId>com.azoraqua</groupId>
    <artifactId>language-api</artifactId>
    <version>{VERSION}</version>
  </dependency>
  ```
- Gradle (Groovy):
  ```groovy
  implementation 'com.azoraqua:language-api:{VERSION}'
  ```
- Gradle (Kotlin)
  ```kt
  implementation("com.azoraqua:language-api:{VERSION}")
  ```


## How to use:
- Registration:
  ```kt
  LanguageAPI.registerTranslation("hello.world", "Hello World", Locale.ENGLISH)
  ```
- Retrieval:
  ```kt
  // Optionally, provide a locale if you want the translation in a different language other than the system's default.
  LanguageAPI.getTranslation("hello.world")
  ```
