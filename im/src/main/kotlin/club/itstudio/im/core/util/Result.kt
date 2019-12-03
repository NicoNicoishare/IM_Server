package club.itstudio.im.core.util

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * http 返回结果 格式
 */
data class Result<T>(
        var status: Int,                // 状态码
        var message: String,            // 状态信息
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var data: T?                    // 数据，null则无此参数返回
)

/**
 * 常见结果集
 */
enum class ResultEnum (val status: Int, val message: String){
        SUCCESS(200, "success"),        // 成功
        ERROR(400, "error");            // 失败
}


object ResultUtil {
        fun success(msg: String, data: Any?): Result<Any> = Result(ResultEnum.SUCCESS.status, msg, data)

        fun success(data: Any?): Result<Any> = success(ResultEnum.SUCCESS.message, data)

        fun success(msg: String): Result<Any> = success(msg, null)

        fun success(): Result<Any> = success(ResultEnum.SUCCESS.message)

        fun error(status: Int, msg: String, data: Any?): Result<Any> = Result(status, msg, data)

        fun error(msg: String, data: Any?): Result<Any> = error(ResultEnum.ERROR.status, msg, data)

        fun error(status: Int, msg: String): Result<Any> = error(status, msg, null)

        fun error(msg: String): Result<Any> = error(ResultEnum.ERROR.status, msg)

        fun error(): Result<Any> = error(ResultEnum.ERROR.message)
}


