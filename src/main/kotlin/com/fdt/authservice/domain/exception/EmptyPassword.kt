package com.fdt.authservice.domain.exception

import java.lang.RuntimeException

class EmptyPassword(msg: String) : RuntimeException(msg)