package pl.denisolek.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import java.io.IOException

@Throws(IOException::class)
fun <T> convertJsonBytesToObject(content: String, clazz: Class<T>): T {
    val mapper = ObjectMapper()
    mapper.findAndRegisterModules()
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    mapper.dateFormat = ISO8601DateFormat()
    return mapper.readValue(content, clazz)
}