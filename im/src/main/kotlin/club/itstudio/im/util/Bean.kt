package club.itstudio.im.util

import club.itstudio.im.core.util.SpringContext
import club.itstudio.im.service.MessageService
import org.springframework.jdbc.core.JdbcTemplate

/**
 * 收集一些常见bean，以供非注入方式使用
 */
object Bean {

    /**
     * jdbcTemplate
     */
    fun jdbcT() = SpringContext.getBean(JdbcTemplate::class.java)

    /**
     * ServerEndpoint 不允许自动注入，因此消息处理 使用非注入方式
     */
    fun msgService() = SpringContext.getBean(MessageService::class.java)

}