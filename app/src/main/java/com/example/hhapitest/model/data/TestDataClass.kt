package com.example.hhapitest.model.data
data class TestDataClass(val items: TestDataClass2)
data class TestDataClass2(val contacts: String?,
                         val schedule: Schedule,
                         val working_days: List<NNUL?>)
