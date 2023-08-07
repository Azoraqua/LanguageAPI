# LanguageAPI
A simple library for providing translations in projects.

## Examples:
- Registration:
  ```kt
  LanguageAPI.registerTranslation("hello.world", "Hello World", Locale.ENGLISH)
  ```
- Retrieval:
  ```kt
  // Optionally, provide a locale if you want the translation in a different language other than the system's default.
  LanguageAPI.getTranslation("hello.world")
  ```
