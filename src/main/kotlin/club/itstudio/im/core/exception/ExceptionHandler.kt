package club.itstudio.im.core.exception

import club.itstudio.im.core.util.Result
import club.itstudio.im.core.util.ResultUtil
import club.itstudio.im.core.util.logger
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.lang.RuntimeException

@ControllerAdvice
class ExceptionHandler {

    private val logger = logger()

    @ResponseBody
    @ExceptionHandler(ITOUCException::class)
    fun runtimeExceptionHandler(e: ITOUCException): Result<Any> {
        val firstTrace = e.stackTrace.first()
        if (firstTrace != null) println(firstTrace)
        return ResultUtil.error(e.status, e.message)
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): Result<Any> {
        return ResultUtil.error(e.message!!)
    }
}