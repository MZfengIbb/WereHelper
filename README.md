# WereHelper

	Current version: v1.0.0
	SinVoice module: "https://github.com/JesseGu/SinVoice"


# Android Studio 2.3.3
##/// app/build.gradle
	// Top-level build file where you can add configuration options common to all sub-projects/modules.
	
	buildscript {
	    repositories {
	        jcenter()
	        maven {
	            url 'https://dl.google.com/dl/android/maven2'
	        }
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:2.3.3'
		
	        // NOTE: Do not place your application dependencies here; they belong
	        // in the individual module build.gradle files
	    }
	}

	allprojects {
	    repositories {
	        jcenter()
	    }
	}
	
	task clean(type: Delete) {
	    delete rootProject.buildDir
	}

##/// build.gradle
	apply plugin: 'com.android.application'
	
	android {
	    compileSdkVersion 25
	    buildToolsVersion "25.0.3"
	    defaultConfig {
	        applicationId "com.ibb.werehelper"
	        jackOptions {
	            enabled true
	        }
	        minSdkVersion 15
	        targetSdkVersion 25
	        versionCode 1
	        versionName "1.0"
	        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
	    }
	    buildTypes {
	        release {
	            minifyEnabled false
	            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
	        }
	    }
	    compileOptions {
	        targetCompatibility 1.8
	        sourceCompatibility 1.8
	    }
	}

	dependencies {
	    compile fileTree(dir: 'libs', include: ['*.jar'])
	    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
	        exclude group: 'com.android.support', module: 'support-annotations'
	    })
	    compile 'com.android.support:appcompat-v7:25.3.1'
	    compile 'com.android.support:recyclerview-v7:25.3.1'
	    compile 'com.android.support:cardview-v7:25.3.1'
	    testCompile 'junit:junit:4.12'
	}
