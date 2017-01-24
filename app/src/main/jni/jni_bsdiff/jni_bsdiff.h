//
// Created by ND on 2016/10/26.
//

#ifndef BSDIFF_UTILS_H
#define BSDIFF_UTILS_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif
extern int diff(int argc, char *argv[]);
extern int patch(int argc, char *argv[]);

//定义方法宏，用于拼接方法名
#define JNI_METHOD(METHOD_NAME) \
  Java_com_rhg_qf_update_BsDiff_##METHOD_NAME

JNIEXPORT jint JNICALL JNI_METHOD(diff)(JNIEnv *env, jobject object,
                                        jstring old_path, jstring new_path, jstring patch_path);

JNIEXPORT jint JNICALL JNI_METHOD(patch)(JNIEnv *env, jobject object,
                                         jstring old_path, jstring new_path, jstring patch_path);


#ifdef __cplusplus
}
#endif
#endif //BSDIFF_UTILS_H
