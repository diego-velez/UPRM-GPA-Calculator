import kotlinx.serialization.Serializable

@Serializable
/**
 * This class represents a single course.
 *
 * @param code The unique code of the course.
 * @param name The name of the course.
 * @param credits How many credits the course is worth.
 * @param grade The grade achieved in the course. Default is W, which does not affect the GPA, but appears in the
 * transcript.
 */
data class Course(val code: String, val name: String, val credits: Int, val grade: String = "W") {
    private val gradeWeight: Int
        get() {
            return when (grade) {
                "A" -> 4
                "B" -> 3
                "C" -> 2
                "D" -> 1
                "F" -> 0
                else -> throw IllegalStateException("Grade achieved cannot be $grade, it must be between A and F")
            }
        }

    val isConcentration = code.endsWith("*")

    val pointsOfHonor: Int
        get() = credits * gradeWeight
}
