# LanguageAPI ![](https://badgen.net/github/tag/Azoraqua/LanguageAPI?label=latest&color=green)
A simple library for introducing multiple languages into projects.  

## Installation:
- Maven:
  ```xml
  <!-- Make sure that your credentials are present in .m2/settings.xml -->
  <repository>
    <id>github-repo</id> 
    <url>https://maven.pkg.github.com/Azoraqua/*</url>
  </repository>
  
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
