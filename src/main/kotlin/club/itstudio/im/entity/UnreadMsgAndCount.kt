package club.itstudio.im.entity

import club.itstudio.im.core.exception.ITOUCException
import club.itstudio.im.util.Bean
import org.springframework.jdbc.core.RowMapper
import java.lang.Exception
import java.sql.ResultSet

/**
 * 未读消息
 */
data class UnreadMsgAndCount (
        var id: Int,
        val to: Int,        // 收件人ID
        val from: Int,      // 发件人ID或群ID
        val messageID: Int, // 最后一条消息ID
        var count: Int      // 未读数量
){

    companion object{
        // 根据ID获取
        fun getByID(id: Int): UnreadMsgAndCount {
            val sql = "SELECT * FROM ins_api_userlastunreadmessageandcount WHERE id=?"
            return Bean.jdbcT().queryForObject(sql, UnReadMsgAndCountMapper(), id) ?:
                    throw ITOUCException(message = "不存在ID=$id 的未读消息")
        }
        // 获取用户未读消息列表
        fun getListByToUser(toUser: Int): List<UnreadMsgAndCount>{
            val sql = "SELECT * FROM ins_api_userlastunreadmessageandcount WHERE `to`=?"
            return Bean.jdbcT().query(sql, UnReadMsgAndCountMapper(), toUser)
        }

        // 用户下线，无法收到消息，更新未读消息列表
        fun update(msg: Message, userID: Int) {
            val type = msg.type
            // 若是个人消息，记录发件人，若是群消息，记录群ID
            val userFrom = if (type in 0..1) msg.from else msg.to
            val count = getCount(userID, userFrom) + 1

            val sql = "REPLACE INTO ins_api_userlastunreadmessageandcount(`to`, userFrom, messageID, count) VALUES(?, ?, ?, ?)"
            Bean.jdbcT().update(sql, userID, userFrom, msg.id, count)
        }

        // 获取用户 未读消息数量
        private fun getCount(userID: Int, userFrom: Int): Int {
            val sql = "SELECT * FROM ins_api_userlastunreadmessageandcount WHERE `to`=? AND userFrom=?"
            return try {
                val unreadMsgAndCount = Bean.jdbcT().queryForObject(sql, UnReadMsgAndCountMapper(), userID, userFrom)
                unreadMsgAndCount?.count ?: 0
            }catch (e: Exception) {0}
        }

        // 已读消息，删除记录
        fun deleteRecordByID(id: Int) {
            val sql = "DELETE FROM ins_api_userlastunreadmessageandcount WHERE id=?"
            Bean.jdbcT().update(sql, id)
        }
    }
}

class UnReadMsgAndCountMapper: RowMapper<UnreadMsgAndCount> {
    override fun mapRow(p0: ResultSet, p1: Int): UnreadMsgAndCount? {
        val id = p0.getInt("id")
        val to = p0.getInt("to")
        val userFrom = p0.getInt("userFrom")
        val messageID = p0.getInt("messageID")
        val count = p0.getInt("count")
        return UnreadMsgAndCount(id, to, userFrom, messageID, count)
    }
}