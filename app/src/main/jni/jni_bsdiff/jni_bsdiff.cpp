#include "jni_bsdiff.h"

JNIEXPORT jint JNICALL JNI_METHOD(diff)(JNIEnv *env, jobject object,
                                        jstring old_path, jstring new_path, jstring patch_path) {
    int argc = 4;
    char *argv[argc];
    argv[0] = (char *) "bsdiff";
    argv[1] = (char *) (env)->GetStringUTFChars(old_path, 0);
    argv[2] = (char *) (env)->GetStringUTFChars(new_path, 0);
    argv[3] = (char *) (env)->GetStringUTFChars(patch_path, 0);
    bool isCrash = false;
    int ret;
    try {
        ret = diff(argc, argv);
    }
    catch (...) {
        isCrash = true;
    }
    (env)->ReleaseStringUTFChars(old_path, argv[1]);
    (env)->ReleaseStringUTFChars(new_path, argv[2]);
    (env)->ReleaseStringUTFChars(patch_path, argv[3]);
    return isCrash ? -1 : ret;
}

JNIEXPORT jint JNICALL JNI_METHOD(patch)(JNIEnv *env, jobject object,
                                         jstring old_path, jstring new_path, jstring patch_path) {
    int argc = 4;
    char *argv[argc];
    argv[0] = (char *) "bspatch";
    argv[1] = (char *) (env)->GetStringUTFChars(old_path, 0);
    argv[2] = (char *) (env)->GetStringUTFChars(new_path, 0);
    argv[3] = (char *) (env)->GetStringUTFChars(patch_path, 0);
    bool isCrash = false;
    int ret;
    try {
        ret = patch(argc, argv);
    }
    catch (...) {
        isCrash = true;
    }
    (env)->ReleaseStringUTFChars(old_path, argv[1]);
    (env)->ReleaseStringUTFChars(new_path, argv[2]);
    (env)->ReleaseStringUTFChars(patch_path, argv[3]);
    return isCrash ? -1 : ret;
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    // Get jclass with env->FindClass.
    // Register methods with env->RegisterNatives.
    return JNI_VERSION_1_6;
}
