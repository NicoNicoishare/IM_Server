package club.itstudio.im.service

import club.itstudio.im.core.util.toJson
import club.itstudio.im.entity.GroupMember
import club.itstudio.im.entity.Message
import club.itstudio.im.entity.UnreadMsgAndCount
import club.itstudio.im.listener.SendMsgChannelListener
import org.springframework.stereotype.Service

@Service
class MessageService {

    // 处理消息
    fun process(message: Message) {
        if (message.type in 0..1){
            processSingleMsg(message)
        }else{
            processGroupMsg(message)
        }
    }

    // 处理个人消息
    fun processSingleMsg(msg: Message) {
        send(msg, msg.to)
    }

    // 处理群消息
    fun processGroupMsg(msg: Message) {
        val from = msg.from
        if (GroupMember.checkInGroup(msg.to, from)) {
            send(Message(text = "您不在群聊中"), from)
        }else {
            // 获取群成员
            val members = GroupMember.getMembersByGroupID(msg.to)
            if (members.isNotEmpty()) {
                members.forEach {
                    send(msg, it.userID)
                }
            }
        }

    }


    /**
     * 发送消息
     * @param msg: 消息内容
     * @param userID: 收件人ID
     */
    fun send(msg: Message, userID: Int) {
        val session = SessionService.getSessionByUserID(userID)
        // 会话不存在或未连接 存入未读消息
        if (session == null || !session.isActive) {
            UnreadMsgAndCount.update(msg, userID)
        }else { // 会话仍存活
            val future = session.sendText(toJson(msg))
            // 添加结果监听器，若发送失败，仍存入未读消息
            future.addListener(SendMsgChannelListener(msg, userID))
        }
    }
}