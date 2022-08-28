package cafe.nekohouse.nekoapi

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UserData(@Contextual val uin: Number, val skey: String, val pskey: String) {
    var cookie: String = "uin=o$uin; skey=$skey; p_uin=o$uin; p_skey=$pskey;"
}

@Serializable
data class ZoneData(
    //@Contextual val code: Number,
    val msglist: Array<Msg>,
    val name: String
) {
    fun getMsg(): String {
        var content = ""
        msglist.forEach {
            content += it.content + "\n"
        }
        return content
    }
}

@Serializable
data class Msg(
    //val cmtnum: Number,
    val commentlist: Array<Comment>,
    val content: String,
    val name: String,
    val tid: String
)

@Serializable
data class Comment(
    val content: String,
    val name: String,
    //@Contextual val tid: Number,
    //@Contextual val uin: Number
)