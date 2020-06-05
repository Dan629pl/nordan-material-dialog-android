# Nordan Material Dialog
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)


## Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	...
	implementation 'com.github.Dan629pl:NordanMaterialDialog:1.0.2'
}
```
<h2>Nordan Alert Dialog</h2>

<h1>Custom Dialog</h1>

```diff
        new NordanAlertDialog.Builder(this)
                .setAnimation(Animation.POP)
                .isCancellable(false)
                .setTitle("Dialog Title")
                .setMessage("Your message")
                .setPositiveBtnText("Ok")
                .setNegativeBtnText("Cancel")
                .setIcon(R.drawable.your_drawable)
                .setBackgroundColor(R.color.red)
                .onPositiveClicked(() -> {/* Do something here */})
                .onNegativeClicked(() -> {/* Do something here */})
                .build().show();
```
<h1>Minimal Dialog</h1>

```diff
              new NordanAlertDialog.Builder(this)
                      .setTitle("Dialog Title")
                      .setMessage("Your message")
                      .setPositiveBtnText("single button")
                      .onPositiveClicked(() -> {/* Do something here */})
                      .build().show();
```
<h1>Dialog with DialogType</h1>

```diff
        new NordanAlertDialog.Builder(this)
                    .setDialogType(DialogType.SUCCESS)
                    .setAnimation(Animation.SLIDE)
                    .isCancellable(true)
                    .setTitle("Success!")
                    .setMessage("Level complete!")
                    .setPositiveBtnText("Great!")
                    .onPositiveClicked(() -> {/* Do something here */})
                    .build().show();
```

<h2>Nordan Loading Dialog</h2>

```diff
  NordanLoadingDialog.createLoadingDialog(this,"Loading...").show();
```
![Loading Dialog](https://github.com/Dan629pl/NordanMaterialDialog/blob/master/img/loading.gif)

## Screenshot

![Dialogs](https://github.com/Dan629pl/NordanMaterialDialog/blob/master/img/dialogs.png)

## Donation
If this library  help you reduce time to develop, you can buy me a coffee! :) 

<a href="https://www.buymeacoffee.com/Dan629"><img src="https://www.buymeacoffee.com/assets/img/bmc-meta-new/apple-icon-72x72.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

```
Copyright 2020 Daniel Owczarczyk

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.