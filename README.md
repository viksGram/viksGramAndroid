<img src="https://i.imgur.com/5EmxevP.png" width="150" align="left"/>

## viksGram
*[Licensed under the GNU General Public License v2.0](https://github.com/viksGram/viksGramAndroid/blob/default/LICENSE)*

Experimental **third-party** Telegram client based on [official sources](https://github.com/DrKLO/Telegram).

[![Channel](https://img.shields.io/badge/Channel-Telegram-blue.svg)](https://t.me/CyboDress)
[![Chat](https://img.shields.io/badge/Chat-Telegram-blue.svg)](https://t.me/CyboDressUi)

## Importing API hash and keys
- You should get **YOUR OWN API KEY AND HASH** here: https://core.telegram.org/api/obtaining_api_id and create a file called `API_KEYS` in the source root directory.
- Also you should get **YOUR OWN MAPS API KEY** here: https://console.cloud.google.com/google/maps-apis/credentials and add it to this file.
- And you need to generate **SIGNING KEY**: https://developer.android.com/studio/publish/app-signing#generate-key

The file content should look like this:
```
APP_ID = 123456
APP_HASH = abcdef0123456789 (32 chars)
MAPS_V2_API = abcdef01234567-abcdef012345678910111213

SIGNING_KEY_PASSWORD = A1BcDEFHJ2KLMn3oP
SIGNING_KEY_ALIAS = abcdefghjklm
SIGNING_KEY_STORE_PASSWORD = Z9yXDEFHJ6KRqn7oP
```

## Compilation Guide
1. Clone exteraGram's source code using `git clone https://github.com/viksGram/viksGramAndroid.git`
2. Fill out values in `API_KEYS` like [here](https://github.com/viksGram/viksGramAndroid#importing-api-hash-and-keys)
3. Open the project in Android Studio. It should be opened, **not imported**
4. You are ready to compile `viksGram`

- **viksGram** can be built with **Android Studio** or from the command line with **Gradle**:
```
./gradlew assembleAfatRelease
```

 ## Thanks to:
- [Catogram](https://github.com/Catogram/Catogram)
- [Nekogram](https://gitlab.com/Nekogram/Nekogram)
- [OwlGram](https://github.com/OwlGramDev/OwlGram)
- [cybb for help](https://github.com/CybCroww)
