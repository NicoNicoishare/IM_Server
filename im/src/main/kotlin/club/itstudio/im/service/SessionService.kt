package club.itstudio.im.service

import io.netty.channel.ChannelId
import org.yeauty.pojo.Session
import java.util.concurrent.ConcurrentHashMap

object SessionService {
    private val userIDToSessionMap = ConcurrentHashMap<Int, Session>()
    private val sessionIDToUserIDMap = ConcurrentHashMap<ChannelId, Int>()

    // 添加会话
    fun addSession(session: Session, userID: Int) {
        userIDToSessionMap[userID] = session
        sessionIDToUserIDMap[session.id()] = userID
    }

    // 根据userID获取会话
    fun getSessionByUserID(userID: Int): Session?{
        return userIDToSessionMap[userID]
    }

    // 通过会话获取userID
    fun getUserIDBySession(session: Session): Int{
        return sessionIDToUserIDMap[session.id()] ?: 0
    }

    // 通过用户ID移除回话
    fun removeSessionByUserID(userID: Int) {
        if (userIDToSessionMap.contains(userID)) {
            val session = userIDToSessionMap[userID]
            sessionIDToUserIDMap.remove(session?.id())
            userIDToSessionMap.remove(userID)
        }
    }

    // 通过会话ID移除会话
    fun removeSessionBySessionId(sessionId: ChannelId) {
        if (sessionIDToUserIDMap.contains(sessionId)) {
            val userID = sessionIDToUserIDMap[sessionId]
            userIDToSessionMap.remove(userID)
            sessionIDToUserIDMap.remove(sessionId)
        }
    }

}