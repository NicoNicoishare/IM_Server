package club.itstudio.im.core.util

import org.apache.tomcat.util.codec.binary.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec


/**
 * 加解密、哈希工具
 */

object Encrypt {
    /**
     * AES 加密
     */
    private const val Transformation = "AES/ECB/PKCS5Padding"

    fun  encAES(content: String, key: String): String{
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        val cipher = Cipher.getInstance(Transformation)
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key.toByteArray(), "AES"))
        val b: ByteArray = cipher.doFinal(content.toByteArray())
        return Base64.encodeBase64String(b)
    }

    /**
     * AES 解密
     */
    fun decAES(encryptStr: String, key: String): String {
        val kgen = KeyGenerator.getInstance("AES")
        kgen.init(128)
        val cipher = Cipher.getInstance(Transformation)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key.toByteArray(), "AES"))
        val encryptBytes = Base64.decodeBase64(encryptStr)
        val decryptBytes = cipher.doFinal(encryptBytes)
        return String(decryptBytes)
    }
}



object HASH {
    /**
     * @param method: hash方法
     * @param str: 需hash的字符
     * @param iterate: 迭代次数
     */
    private fun hash(method: String, str: String,  iterate: Int): String{
        val digest = MessageDigest.getInstance(method)
        var encryptStr = str
        for (i in 0 until iterate) {
            encryptStr =  toHex(digest.digest(encryptStr.toByteArray()))
        }
        return encryptStr
    }

    private fun toHex(byteArray: ByteArray): String {
        return with(StringBuilder()) {
            byteArray.forEach {
                val hex = it.toInt() and (0xFF)
                val hexStr = Integer.toHexString(hex)
                if (hexStr.length == 1) {
                    this.append("0").append(hexStr)
                } else {
                    this.append(hexStr)
                }
            }
            this.toString()
        }
    }


    fun md5(str: String, iterate: Int): String = hash("MD5", str, iterate)
    fun md5(str: String) = md5(str, 1)


    fun sha1(str: String, iterate: Int): String = hash("SHA-1", str, iterate)
    fun sha1(str: String) = sha1(str, 1)

    fun sha256(str: String, iterate: Int): String = hash("SHA-256", str, iterate)
    fun sha256(str: String) = sha256(str, 1)
}

