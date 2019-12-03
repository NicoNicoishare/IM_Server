package club.itstudio.im.config

import club.itstudio.im.core.exception.SessionInvalid
import club.itstudio.im.entity.Token
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.lang.Exception

@Aspect
@Component
class LoginRequiredHandler {
    object LoginToken{
        val tokenMap = mutableMapOf<String, Int>()
    }
    @Around("@annotation(club.itstudio.im.core.annotation.LoginRequired)")
    fun access(jointPoint: ProceedingJoinPoint): Any {
        try {
            val tokenKey = getTokenKey()
            if (!LoginToken.tokenMap.containsKey(tokenKey)) {
                Token.getByKey(tokenKey).also { LoginToken.tokenMap[it.key] = it.userID }
            }
        }catch (e: Exception) {
            throw SessionInvalid()
        }
        return jointPoint.proceed()
    }

    fun getTokenKey(): String {
        val sra = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val auth = sra.request.getHeader("Authorization")
        if (auth != null && auth.length > 6) {
            return auth.substring(6)
        }
        throw SessionInvalid()
    }
}
