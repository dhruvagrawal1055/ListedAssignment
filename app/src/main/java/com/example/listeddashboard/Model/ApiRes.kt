package com.example.listeddashboard.Model

// data model for overall API response
data class ApiRes(
    val status: Boolean,
    val statusCode: Int,
    val message: String,
    val support_whatsapp_number: String,
    val extra_income: Double,
    val total_links: Int,
    val total_clicks: Int,
    val today_clicks: Int,
    val top_source: String,
    val top_location: String,
    val startTime: String,
    val links_created_today: Int,
    val applied_campaign: Int,
    val data: DashboardData
)
// data model for Data object from API response
data class DashboardData(
    val recent_links: List<RecentLink>,
    val top_links: List<TopLink>,
    val overall_url_chart: Map<String, Int>
)
// data model for recent link parameters from API response
data class RecentLink(
    val url_id: Int,
    val web_link: String,
    val smart_link: String,
    val title: String,
    val total_clicks: Int,
    val original_image: String?,
    val thumbnail: String?,
    val times_ago: String,
    val created_at: String,
    val domain_id: String,
    val url_prefix: String?,
    val url_suffix: String,
    val app: String
)
// data model for top link parameters from API response
data class TopLink(
    val url_id: Int,
    val web_link: String,
    val smart_link: String,
    val title: String,
    val total_clicks: Int,
    val original_image: String?,
    val thumbnail: String?,
    val times_ago: String,
    val created_at: String,
    val domain_id: String,
    val url_prefix: String?,
    val url_suffix: String,
    val app: String
)
// data model for our top links and recent links tabs
data class ListCustom(
    val title: String,
    val total_clicks: Int,
    val original_image: String?,
    val smart_link: String,
    val created_at: String
)


