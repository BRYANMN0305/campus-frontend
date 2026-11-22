package co.edu.iub.campus_app.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorCodeStore @Inject constructor(
    @ApplicationContext context: Context
) {

    private val preferences = context.getSharedPreferences("campus_prefs", Context.MODE_PRIVATE)

    fun getOrCreate(): String {
        preferences.getString(KEY_AUTHOR_CODE, null)
            ?.takeIf { it.isNotBlank() }
            ?.let { return it }

        val code = "EST-" + UUID.randomUUID()
            .toString()
            .replace("-", "")
            .take(6)
            .uppercase()

        preferences.edit().putString(KEY_AUTHOR_CODE, code).apply()
        return code
    }

    private companion object {
        const val KEY_AUTHOR_CODE = "author_code"
    }
}
