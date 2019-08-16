package com.funny.appframework.data.net.exception

/**
 * @author pengl
 */
class ServerException : RuntimeException() {
    var code: Int = 0
    override var message: String = ""
}