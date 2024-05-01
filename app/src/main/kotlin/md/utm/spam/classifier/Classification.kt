package md.utm.spam.classifier

sealed interface Classification {

    val probability: Float
}

data class Spam(override val probability: Float) : Classification
data class Ham(override val probability: Float) : Classification
