package club.itstudio.im.socket

import club.itstudio.im.core.util.mapper
import club.itstudio.im.core.util.toJson
import club.itstudio.im.entity.Message
import club.itstudio.im.entity.Token
import club.itstudio.im.service.SessionService
import club.itstudio.im.util.Bean
import com.fasterxml.jackson.module.kotlin.readValue
import io.netty.handler.codec.http.HttpHeaders
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent
import org.springframework.stereotype.Component
import org.yeauty.annotation.*
import org.yeauty.pojo.ParameterMap
import org.yeauty.pojo.Session
import java.lang.Exception


@ServerEndpoint(prefix = "netty-websocket")
@Component
class MyWebSocket{
    @OnOpen
    fun onOpen(session: Session, headers: HttpHeaders, paramMap: ParameterMap) {
        val tokenKey = paramMap.getParameter("token") ?: ""
        if (tokenKey.isNotEmpty()) {
            try {
                // 添加会话
                val token = Token.getByKey(tokenKey)
                SessionService.addSession(session, token.userID)
                println("添加会话：$tokenKey")
            }catch (e: Exception) {
                // 结束会话
                val message = Message(text = "token不存在" )
                session.sendText(toJson(message))
                session.close()
            }
        }
    }

    @OnClose
    fun onClose(session: Session) {
        session.close()
        SessionService.removeSessionBySessionId(session.id())
    }

    @OnError
    fun onError(session: Session, throwable: Throwable) {
        session.close()
        SessionService.removeSessionBySessionId(session.id())
    }

    @OnMessage
    fun onMessage(session: Session, message: String) {
        // 长度小于一认为是心跳检测
        if (message.length > 1) {
            // 序列化
            val msg: Message = mapper.readValue(message)
            // 添加发件人
            val userID = SessionService.getUserIDBySession(session)
            msg.from = userID

            // 存入数据库，并获取ID
            Message.save(msg)
            // 处理消息
            Bean.msgService().process(msg)
        }
    }

    @OnBinary
    fun onnBinary(session: Session, bytes: ByteArray) {
    }

    @OnEvent
    fun onEvent(session: Session, evt: Any) {
        if (evt is IdleStateEvent) {
            when (evt.state()) {
                IdleState.READER_IDLE -> println("read idle")
                IdleState.WRITER_IDLE -> println("write idle")
                IdleState.ALL_IDLE -> println("all idle")
                else -> {
                }
            }
        }
    }
}