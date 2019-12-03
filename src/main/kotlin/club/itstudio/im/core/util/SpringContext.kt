package club.itstudio.im.core.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component


@Component
class SpringContext: ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        Companion.applicationContext = applicationContext
    }

    companion object {
        private var applicationContext: ApplicationContext? = null

        /**
         * 获取applicationContext
         */
        private fun getApplicationContext(): ApplicationContext? {
            return applicationContext
        }

        /**
         * 通过name获取 Bean.
         */
        fun getBean(name: String): Any {
            return getApplicationContext()!!.getBean(name)
        }

        /**
         * 通过class获取Bean.
         */
        fun <T> getBean(clazz: Class<T>): T {
            return getApplicationContext()!!.getBean(clazz)
        }

        /**
         * 通过name,以及Clazz返回指定的Bean
         */
        fun <T> getBean(name: String, clazz: Class<T>): T {
            return getApplicationContext()!!.getBean(name, clazz)
        }

        /**
         * 获取配置文件配置项的值
         * @param key 配置项key
         */
        fun getEnvironmentProperty(key: String): String {
            return getApplicationContext()!!.environment.getProperty(key) ?: ""
        }
    }
}