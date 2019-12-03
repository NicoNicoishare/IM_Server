package club.itstudio.im.entity

import club.itstudio.im.util.Bean
import org.springframework.jdbc.core.RowMapper
import java.lang.Exception
import java.sql.ResultSet

/**
 * 登录token
 */
data class Token (
        val key: String,
        val userID: Int
) {
    companion object{
        // 根据key 获取 token 不存在抛异常
        fun getByKey(key: String): Token {
            val sql = "SELECT `key`, user_id FROM authtoken_token WHERE `key`=?"
            val token = Bean.jdbcT().queryForObject(sql, TokenMapper(), key)
            return token ?: throw Exception("不存在 key=$key 的token")
        }
    }
}

class TokenMapper: RowMapper<Token>{
    override fun mapRow(p0: ResultSet, p1: Int): Token? {
        val key = p0.getString("key")
        val userId = p0.getInt("user_id")
        return Token(key, userId)
    }
}