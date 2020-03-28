package com.fdt.authservice.domain.exception

import java.lang.RuntimeException

class AlreadyTakenMailOrPhone(msg: String) : RuntimeException(msg)