# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Work\Android\software\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

 #实体类不参与混淆
-keep class com.rhg.qf.bean.** { *; }
#自定义控件不参与混淆
-keep class com.rhg.qf.widget.** { *; }

# ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# 微信支付
-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.** {*;}
-keep class com.tencent.wxop.stat.**{*;}

# 支付宝钱包
-dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*

# apache
-dontwarn org.apache.**
-keep class org.apache.** {*;}

# 百度地图
-dontwarn com.baidu.**
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
# 百度地图
-dontwarn com.google.android.gms.**
-keep class com.google.android.ms.**

-dontwarn javax.xml.stream.events.**
-dontwarn javax.xml.stream.**
-keep class javax.xml.stream.events.**
-keep class javax.xml.stream.**

-dontwarn org.slf4j.impl.**
-keep class org.slf4j.impl.**




# 友盟
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebViewClient
-dontwarn android.net.http.SslCertificate
-dontwarn com.umeng.**
-keep public class javax.**
-keep public class android.webkit.WebViewClient
-keep public class android.net.http.SslCertificate
-dontwarn android.support.v4.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep class com.tencent.connect.** {*;}
-dontwarn com.tencent.connect.**

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

-keepattributes Signature

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
