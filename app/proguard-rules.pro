-keep class com.example.BuildConfig { *; }
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v7.app.ActionBarActivity
-keep public class * extends com.power2sme.android.sections.SuperFragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.billing.IInAppBillingService
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.content.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}

-libraryjars libs
# google map.##########################################################
-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }

# The official support library.##########################################################
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v4.annotation.** { *; }
-keep interface android.support.v4.annotation.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

## Google Play Services 4.3.23 specific rules ##
## https://developer.android.com/google/play-services/setup.html#Proguard ##

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

## Google Analytics 3.0 specific rules ##

-keep class com.google.analytics.** { *; }
-keep class com.google.android.gms.analytics.** { *; }


# Proguard configuration for Jackson 2.x (fasterxml package instead of codehaus package)

-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}
-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public ** writeValueAsString(**);
}

# Crashlytics 1.+

-keep class com.crashlytics.** { *; }
-keepattributes SourceFile,LineNumberTable

# Facebook 3.2

-keep class com.facebook.** { *; }
-keepattributes Signature

# Library JARs.#########################################################

#commons-codec-1.3.jar
-keep class org.apache.commons.codec.** { *; }
-keep interface org.apache.commons.codec.** { *; }

-keep class org.apache.commons.codec.binary.** { *; }
-keep interface org.apache.commons.codec.binary..** { *; }

-keep class org.apache.commons.codec.digest.** { *; }
-keep interface org.apache.commons.codec.digest.** { *; }

-keep class org.apache.commons.codec.language.** { *; }
-keep interface org.apache.commons.codec.language.** { *; }

-keep class org.apache.commons.codec.net.** { *; }
-keep interface org.apache.commons.codec.net.** { *; }

#linkedin-j-android.jar
-keep class com.google.code.linkedinapi.client.constant.** { *; }
-keep interface com.google.code.linkedinapi.client.constant.** { *; }
-keep class com.google.code.linkedinapi.client.enumeration.** { *; }
-keep interface com.google.code.linkedinapi.client.enumeration.** { *; }
-keep class com.google.code.linkedinapi.client.impl.** { *; }
-keep interface com.google.code.linkedinapi.client.impl.** { *; }
-keep class com.google.code.linkedinapi.client.oauth.** { *; }
-keep interface com.google.code.linkedinapi.client.oauth.** { *; }
-keep class com.google.code.linkedinapi.client.** { *; }
-keep interface com.google.code.linkedinapi.client.** { *; }
-keep class com.google.code.linkedinapi.schema.impl.** { *; }
-keep interface com.google.code.linkedinapi.schema.impl.** { *; }
-keep class com.google.code.linkedinapi.schema.xpp.** { *; }
-keep interface com.google.code.linkedinapi.schema.xpp.** { *; }
-keep class com.google.code.linkedinapi.schema.** { *; }
-keep interface com.google.code.linkedinapi.schema.** { *; }
-keep class com.linkedin.** { *; }
-keepattributes Signature

#signpost-core-1.2.1.1.jar
-keep class com.google.gdata.util.common.base.** { *; }
-keep interface com.google.gdata.util.common.base..** { *; }

-keep class oauth.signpost.basic.** { *; }
-keep interface oauth.signpost.basic.** { *; }
-keep class oauth.signpost.exception.** { *; }
-keep interface oauth.signpost.exception.** { *; }
-keep class oauth.signpost.http.** { *; }
-keep interface oauth.signpost.http.** { *; }
-keep class oauth.signpost.signature.** { *; }
-keep interface oauth.signpost.signature.** { *; }
-keep class oauth.signpost.** { *; }
-keep interface oauth.signpost.** { *; }


#SmoothProgressBar Library 
-keep class org.apache.http.** { *; }
-keep interface org.apache.http.** { *; }

#Jackson Library
-keep class com.fasterxml.jackson.core.** { *; }
-keep interface com.fasterxml.jackson.core.** { *; }
-keep class com.fasterxml.jackson.annotation.** { *; }
-keep interface com.fasterxml.jackson.annotation.** { *; }
-keep class com.fasterxml.jackson.databind.** { *; }
-keep interface com.fasterxml.jackson.databind.** { *; }

#-libraryjars libs/jackson-annotations-2.5.0.jar
#-libraryjars libs/jackson-core-2.5.0.jar
#-libraryjars libs/jackson-databind-2.5.0.jar

-dontskipnonpubliclibraryclassmembers

-keepattributes *Annotation*,EnclosingMethod

-keepnames class org.codehaus.jackson.** { *; }

-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**
-dontwarn com.fasterxml.jackson.databind.**

-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}

-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public ** writeValueAsString(**);
}

-keep public class com.power2sme.android.entities.** {
  public void set*(***);
  public *** get*();
}
-keep public class com.power2sme.android.requestdto.** {
  public void set*(***);
  public *** get*();
}
-keep public class com.power2sme.android.responsedto.** {
  public void set*(***);
  public *** get*();
}

# Library projects.#########################################################

#SmoothProgressBar Library 
-keep class fr.castorflex.android.smoothprogressbar.** { *; }
-keep interface fr.castorflex.android.smoothprogressbar.** { *; }

#SuperToast Library 
-keep class com.github.johnpersano.supertoasts.** { *; }
-keep interface com.github.johnpersano.supertoasts.** { *; }

#RSSReader Library 
-keep class nl.matshofman.saxrssreader.** { *; }
-keep interface nl.matshofman.saxrssreader.** { *; }

#SuperRecyclerView Library 
-keep class com.malinskiy.superrecyclerview.** { *; }
-keep interface com.malinskiy.superrecyclerview.** { *; }

#NineOldAndroid Library 
-keep class com.nineoldandroids.** { *; }
-keep interface com.nineoldandroids.** { *; }

#UniversalImageLoader Library 
-keep class com.nostra13.universalimageloader.** { *; }
-keep interface com.nostra13.universalimageloader.** { *; }

#aFileChooser Library
-keep class com.ianhanniballake.localstorage.** { *; }
-keep interface com.ianhanniballake.localstorage.** { *; }
-keep class com.ipaulpro.** { *; }
-keep interface com.ipaulpro.** { *; }

-dontwarn org.apache.http.**
-dontwarn fr.castorflex.android.smoothprogressbar.**
-dontwarn com.malinskiy.superrecyclerview.**
-dontwarn com.ipaulpro.afilechooser.**
-dontwarn com.ianhanniballake.localstorage.**
-dontwarn com.google.android.gms.**
-dontwarn com.github.johnpersano.**
-dontwarn com.facebook.**
-dontwarn com.example.android.**

#Picasso Library
-dontwarn com.squareup.okhttp.**

#V3 Settings Changes
-dontskipnonpubliclibraryclassmembers
-dontwarn com.android.volley.toolbox.**
-dontwarn com.handmark.pulltorefresh.library.**


#AppSee
-keep class com.appsee.** { *; }
-dontwarn com.appsee.**
-optimizations !code/*,!field/*,!class/merging/*,!method/*
-dontwarn android.support.v4.**
-dontwarn com.viewpagerindicator.**
-keep class org.apache.commons.logging.**
-keep interface org.apache.commons.logging.**

-keep class com.h6ah4i.android.widget.advrecyclerview.animator.impl.**
-keep interface com.h6ah4i.android.widget.advrecyclerview.animator.impl.**
-dontwarn android.support.v4.**
-dontwarn com.viewpagerindicator.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.google.android.gms.**
-dontwarn com.android.volley.toolbox.**
-dontwarn javax.naming.**
-dontwarn java.lang.invoke**
-dontwarn org.apache.lang.**
-dontwarn org.apache.commons.**
-dontwarn com.nhaarman.**
-dontwarn se.emilsjolander.**
-dontwarn com.h6ah4i.android.widget.advrecyclerview.animator.impl.**

