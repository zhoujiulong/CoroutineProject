# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#按照三方框架，每个配置一个文件进行管理，同意放到同级 proguard-files 文件夹中
-basedirectory proguard-files

-include base.pro
-include retrofit.pro
-include gson.pro
-include loginterceptor.pro
-include glide.pro
-include baserecyclerviewadapterhelper.pro
-include eventbus.pro
-include arouter.pro



















