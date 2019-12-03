package club.itstudio.im.listener

import club.itstudio.im.entity.Message
import club.itstudio.im.entity.UnreadMsgAndCount
import club.itstudio.im.service.SessionService
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener

// 消息发送结果处理
class SendMsgChannelListener (private val message: Message, private val userID: Int):ChannelFutureListener {
    override fun operationComplete(future: ChannelFuture?) {
        if (future == null) return
        // 如果发送失败
        if (!future.isSuccess) {
            // 添加到未读消息
            UnreadMsgAndCount.update(message, userID)
            // 删除用户会话
            SessionService.removeSessionByUserID(userID)
        }
    }
}