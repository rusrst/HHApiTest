package com.example.foundation

import com.example.foundation.model.Repository

interface BaseApplication {
    val singletonScopeDependencies: List<Any>
}