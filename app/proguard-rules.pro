#---------------------------------1.实体类---------------------------------

#---------------------------------2.第三方包-------------------------------
-keep public class io.reactivex.**{
    *;
}
-keep public class retrofit*.**{
    *;
}

-keep public class com.google.gson.**{
    *;
}
-keep public class okio.**{
     *;
}
-keep public class okhttp3.**{
    *;
}
-keep public class com.jzxiang.**{
    *;
}
-keep public class com.jaeger.**{
    *;
}
-keep public class com.zhy.**{
   *;
}
-keep public class org.reactivestreams.**{
    *;
}

#---------------------------------a.去除警告-----------------------
-dontwarn io.reactivex.**
-dontwarn retrofit*.**
-dontwarn com.google.gson.**
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn com.jzxiang.**
-dontwarn com.jaeger.**
-dontwarn com.zhy.**
-dontwarn org.reactivestreams.**


#---------------------------------3.与js互相调用的类------------------------


#---------------------------------4.反射相关的类和方法-----------------------




#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
#混淆时不会产生形形色色的类名
-dontusemixedcaseclassnames
#指定不去忽略非公共的类库
-dontskipnonpubliclibraryclasses
#不进行预校验
-dontpreverify
-verbose
#优化操作
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要
-keepattributes *Annotation*
# 避免混淆泛型，这在JSON实体映射时非常重要
-keepattributes Signature
#抛出异常时保留代码行号，在异常分析中可以方便定位
-keepattributes SourceFile,LineNumberTable
#这个是给Microsoft Windows用户的，因为ProGuard假定使用的操作系统是能区分两个只是大小写不同的文件名，但是Microsoft Windows不是这样的操作系统，所以必须为ProGuard指定-dontusemixedcaseclassnames选项
-dontusemixedcaseclassnames

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-dontwarn android.support.v4.**
-dontwarn com.android.support.**
-dontwarn android.support.**

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
   }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    }
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}