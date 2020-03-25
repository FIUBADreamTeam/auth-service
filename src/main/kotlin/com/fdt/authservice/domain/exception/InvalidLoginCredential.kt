package com.fdt.authservice.domain.exception

import java.lang.RuntimeException

class InvalidLoginCredential(msg: String) : RuntimeException(msg)