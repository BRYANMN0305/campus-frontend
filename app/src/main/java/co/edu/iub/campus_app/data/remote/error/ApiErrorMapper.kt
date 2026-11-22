package co.edu.iub.campus_app.data.remote.error

import co.edu.iub.campus_app.data.remote.response.ApiErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiErrorMapper @Inject constructor(
    private val gson: Gson
) {

    fun message(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> httpMessage(throwable)
            is IOException -> "No hay conexión con el servidor. Verifica que el backend esté encendido."
            else -> throwable.message ?: "Ocurrió un error inesperado. Inténtalo de nuevo."
        }
    }

    private fun httpMessage(exception: HttpException): String {
        val body = exception.response()?.errorBody()?.string()
        val parsed = body?.let {
            runCatching { gson.fromJson(it, ApiErrorResponse::class.java) }.getOrNull()
        }

        parsed?.message
            ?.takeIf { it.isNotBlank() }
            ?.let { return it }

        return when (exception.code()) {
            400 -> "Revisa los datos e inténtalo de nuevo."
            404 -> "No encontramos la información solicitada."
            500 -> "El servidor tuvo un problema. Inténtalo más tarde."
            else -> "No pudimos completar la solicitud."
        }
    }
}
