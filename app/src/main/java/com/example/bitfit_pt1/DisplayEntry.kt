package com.example.bitfit_pt1

import java.time.Instant


data class DisplayEntry (
    val id: Long?,
    val title: String?,
    val date: Instant?,
    val entry: String?
)