package com.project.domain

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.Nonnull

data class Product(
    val name: String,
    val pricing: Long,
)
