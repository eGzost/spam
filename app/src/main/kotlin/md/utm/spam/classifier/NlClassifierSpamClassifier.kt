package md.utm.spam.classifier

import android.util.Log
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier

fun nlSpamClassifier(classifier: NLClassifier, threshold: Float = 0.8f) = Classifier { target ->
    val (ham, spam) = classifier.classify(target)
    Log.d("Classifier", "$target\nScore: $spam\nHam Score: $ham")
    if (spam.score >= threshold) Spam(spam.score) else Ham(ham.score)
}

