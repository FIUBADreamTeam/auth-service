package com.fdt.authservice.domain.exception

import java.lang.RuntimeException

class UnavailableUserId(msg: String) : RuntimeException(msg)