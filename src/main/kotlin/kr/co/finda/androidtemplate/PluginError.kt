package kr.co.finda.androidtemplate

enum class PluginError(
    val title: String,
    val message: String
) {

    /**
     * Finda Template
     */
    FT101("선택된 위치를 찾을 수 없습니다", "FT101"),
    FT102("유효하지 않은 경로입니다", "FT102-'src/main/(java|kotlin)'경로에 해당하지 않습니다")
}