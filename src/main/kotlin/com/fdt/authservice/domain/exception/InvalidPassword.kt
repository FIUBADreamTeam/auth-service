package com.fdt.authservice.domain.exception

import java.lang.RuntimeException

class InvalidPassword(msg: String) : RuntimeException(msg)