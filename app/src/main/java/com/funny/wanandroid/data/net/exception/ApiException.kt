package com.funny.appframework.data.net.exception

/**
 * @author pengl
 */
class ApiException : Exception {
    var code: Int = 0
    override var message: String = ""

    constructor(message: String, code: Int) : super(message) {
        this.message = message
        this.code = code
    }

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }
}