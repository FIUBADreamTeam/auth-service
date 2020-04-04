package com.fdt.authservice.application.exception

import java.lang.RuntimeException

class NullOrEmptyPassword(msg: String) : RuntimeException(msg)