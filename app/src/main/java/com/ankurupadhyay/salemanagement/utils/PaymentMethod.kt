package com.ankurupadhyay.salemanagement.utils

enum class PaymentMethod(val type:Int) {
    ONLINE(0),
    CASH(1)
}

enum class AnalyticsType(val type:Int) {
    DayByDay(0),
    MonthByMonth(1),
    YearByYear(2)
}

enum class SelectedDate(val type: Int) {
    INCREMENT(0),
    DECREMENT(1)
}

enum class QueryType(val type:String){
    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    NONE("none")
}