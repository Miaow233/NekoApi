package cafe.nekohouse.nekoapi

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*

const val PoemUrl = "https://poem.xiaoice.com/api/upload"
const val userid = "79b688fd-7d23-4dff-b405-d0bba04083ed"
const val guid = "226466e9-e487-46cd-995e-f974514d77e9"
const val testData = """
{
    "IsAdult": false,
    "IsPolitician": false,
    "ShowImage": true,
    "OpenPoems": [
        {
            "OpenPoemID": "c1fe52ee-aeb2-4ee6-91e9-087aff524cec",
            "PoemContent": "山岭的高亢与流水与浪漫\n甜蜜的时候都要去\n可也是人们的爱情\n但寂寂一湾水田上的灯\n\n转变入清冷的天空里\n时间是梦中甜美的思想\n多少人类的幸福\n那过去的太阳已经吞没了",
            "PoemType": "four_four_lines",
            "Score": 0.75,
            "Optimum": true,
            "TimeStamp": "2022.8.28"
        },
        {
            "OpenPoemID": "d992da21-fdb2-45cd-846d-8dff7ea34e62",
            "PoemContent": "操劳的人们减少了许多幸福了\n美人就是我的方向\n静静的湖水忽然向上\n\n陷在世界上的一对青年爱人\n时间是梦中甜美的思想\n他来的时候我再没有无核的爱\n\n你别寻不见太阳光明的火把\n并且是一个梦中醒来",
            "PoemType": "three_three_two_lines",
            "Score": 0.66666666666666663,
            "Optimum": false,
            "TimeStamp": "2022.8.28"
        },
        {
            "OpenPoemID": "7fd2fdb2-ce11-4979-a2f5-361fade3725f",
            "PoemContent": "看人们的爱情之梦\n跑遍了甜蜜的时候了\n你们和幸福的人们的伤痛\n人间有现实的人们的舞台\n\n黯雾遮了太阳的光明的人类\n她待我看作人们的爱情\n我的梦中我的方向\n却是从容的天空里发呆\n\n恨的人们也只得到她的眼泪\n发觉了梦中甜美的爱情\n把人间的余痕都留在梦里遇着\n是一叶的生命的成绩\n又一次湖水各处的草\n我忍受不住世界的声音",
            "PoemType": "four_four_six_lines",
            "Score": 0.47916666666666663,
            "Optimum": false,
            "TimeStamp": "2022.8.28"
        }
    ]
}
"""
val jsonDecoder = Json { ignoreUnknownKeys = true }

@Serializable
data class PoemData(
    val IsAdult: Boolean,
    val IsPolitician: Boolean,
    val ShowImage: Boolean,
    val OpenPoems: Array<OpenPoems>
) {
    fun generateImage() {

    }

    fun getPoem(size: PoemSize = PoemSize.Short): String {
        return when (size) {
            PoemSize.Short -> OpenPoems[0].PoemContent
            PoemSize.Middle -> OpenPoems[1].PoemContent
            PoemSize.Long -> OpenPoems[2].PoemContent
        }
    }
}

@Serializable
data class OpenPoems(
    val OpenPoemID: String,
    val PoemContent: String,
    val PoemType: String,
    val Score: Float,
    val Optimum: Boolean,
    val TimeStamp: String
)

enum class PoemSize {
    Short,  // four_four_lines
    Middle, // three_three_two_lines
    Long    // four_four_six_lines
}

@OptIn(InternalAPI::class)
suspend fun getPoem(pic64: String, text: String = ""): PoemData {
    val client = HttpClient(CIO)
    val data = client.post(PoemUrl) {
        body = MultiPartFormDataContent(formData {
            append("image", pic64)
            append("userid", userid)
            append("guid", guid)
            if (!text.isNullOrBlank()) append("text", text)
        })
    }.bodyAsText()

    return jsonDecoder.decodeFromString<PoemData>(data)
}

@OptIn(InternalAPI::class)
suspend fun main() {
    val img: String =
        Base64.getEncoder().encodeToString(File("C:\\Users\\Miaow\\Desktop\\QQ图片20220828222302.jpg").readBytes())

    val client = HttpClient(CIO)
    val data = client.post(PoemUrl) {
        body = MultiPartFormDataContent(formData {
            append("image", img)
            append("userid", userid)
            append("guid", guid)
        })
    }.bodyAsText()
    jsonDecoder.decodeFromString<PoemData>(data).apply {
        println(getPoem(PoemSize.Short))
        println("-".repeat(10))
        println(getPoem(PoemSize.Middle))
        println("-".repeat(10))
        println(getPoem(PoemSize.Long))
    }

}