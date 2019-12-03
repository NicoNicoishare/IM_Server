package club.itstudio.im.entity

import club.itstudio.im.util.Bean
import club.itstudio.itouc.core.util.toStr
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.ResultSet
import java.sql.Statement

/**
 * 消息
 */
data class Message (
        var id: Int = 0,
        val type: Int = 0,      // 0 个人文本消息 1 个人图片消息 2 群文本消息 3 群图片消息
        val text: String,
        var to: Int = 0,        // type为0、1时代表收件人ID   2、3时代表群ID
        var from: Int = 1,
        val time: Long = 0
) {
    companion object{
        // 存消息，并更新自增ID
        fun save(msg: Message): Boolean{
            val sql = "INSERT INTO ins_api_message(mType, text, `to`, userFrom, time) VALUES(?, ?, ?, ?, ?)"
            val keyHolder = GeneratedKeyHolder()
            val result =  Bean.jdbcT().update({
               val ps = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ps.setInt(1, msg.type)
                ps.setString(2, msg.text)
                ps.setInt(3, msg.to)
                ps.setInt(4, msg.from)
                ps.setLong(5, msg.time)
                ps
            }, keyHolder) == 1
            if (result) msg.id = keyHolder.key?.toInt() ?: -1
            return result
        }

        /**
         * 获取个人消息历史记录
         */
        fun getSingleMsgListByTime(time: Long, from: Int, to: Int): List<Message> {
            val sql = "SELECT * FROM ins_api_message WHERE (userFrom=? AND `to`=?) OR (`to`=? AND userFrom=?) AND mType < 2 AND time < ? ORDER BY time desc limit 20"
            return Bean.jdbcT().query(sql, MessageMapper(), from, to, from, to,  time)
        }

        fun getGroupMsgListByTime(time: Long, groupID: Int): List<Message> {
            val sql = "SELECT * FROM ins_api_message WHERE `to`=? AND mType > 1 AND time < ? ORDER BY time desc limit 20"
            return Bean.jdbcT().query(sql, MessageMapper(), groupID, time)
        }

        /**
         * 根据ID列表获取数据
         */
        fun getMsgListByIDList(idList: List<Int>): List<Message> {
            if (idList.isEmpty()) return emptyList()
            val sql = "SELECT * FROM ins_api_message WHERE id in(${idList.toStr()})"
            return Bean.jdbcT().query(sql, MessageMapper())
        }

    }
}

class MessageMapper: RowMapper<Message> {
    override fun mapRow(p0: ResultSet, p1: Int): Message? {
        val id = p0.getInt("id")
        val type = p0.getInt("mType")
        val text = p0.getString("text")
        val to = p0.getInt("to")
        val from =  p0.getInt("userFrom")
        val time = p0.getLong("time")
        return Message(id, type, text, to, from, time)
    }
}
