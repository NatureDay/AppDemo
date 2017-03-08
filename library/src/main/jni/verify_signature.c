#include "com_qianmo_android_library_utils_JniUtil.h"
#include <stdlib.h>

#define DEFAULT_SIGNATURE -542653066       /* default signature hashcode for this app */

JNIEXPORT void JNICALL
Java_com_qianmo_android_common_utils_JniUtil_verifySignature(JNIEnv *env, jclass thiz,
                                                             jobject obj) {
    // 获得Context类
    jclass cls = (*env)->GetObjectClass(env, obj);
    // 得到getPackageManager方法的ID
    jmethodID mid = (*env)->GetMethodID(env, cls, "getPackageManager",
                                        "()Landroid/content/pm/PackageManager;");

    // 获得应用包的管理器
    jobject pm = (*env)->CallObjectMethod(env, obj, mid);

    // 得到getPackageName方法的ID
    mid = (*env)->GetMethodID(env, cls, "getPackageName", "()Ljava/lang/String;");
    // 获得当前应用包名
    jstring packageName = (jstring) (*env)->CallObjectMethod(env, obj, mid);

    // 获得PackageManager类
    cls = (*env)->GetObjectClass(env, pm);
    // 得到getPackageInfo方法的ID
    mid = (*env)->GetMethodID(env, cls, "getPackageInfo",
                              "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    // 获得应用包的信息
    jobject packageInfo = (*env)->CallObjectMethod(env, pm, mid, packageName,
                                                   0x40); //GET_SIGNATURES = 64;
    // 获得PackageInfo 类
    cls = (*env)->GetObjectClass(env, packageInfo);
    // 获得签名数组属性的ID
    jfieldID fid = (*env)->GetFieldID(env, cls, "signatures", "[Landroid/content/pm/Signature;");
    // 得到签名数组
    jobjectArray signatures = (jobjectArray) (*env)->GetObjectField(env, packageInfo, fid);
    // 得到签名
    jobject sign = (*env)->GetObjectArrayElement(env, signatures, 0);

    // 获得Signature类
    cls = (*env)->GetObjectClass(env, sign);
    // 得到toCharsString方法的ID
    mid = (*env)->GetMethodID(env, cls, "toCharsString", "()Ljava/lang/String;");
    // 获取签名对应的charsString
    jstring charsString = (jstring) (*env)->CallObjectMethod(env, sign, mid);

    cls = (*env)->GetObjectClass(env, charsString);
    mid = (*env)->GetMethodID(env, cls, "hashCode", "()I");

    int hash_code = (int) (*env)->CallIntMethod(env, charsString, mid);
    if (hash_code != DEFAULT_SIGNATURE) {
        exit(1);
    }
}