import kotlinx.serialization.Serializable

@Serializable
/**
 * This class represents a single semester taken.
 * Each semester has a name and a list of courses taken.
 *
 * Can also be used as an object that holds a group of courses.
 *
 * @param name The name of the semester or group of courses.
 * @param courses The list of courses.
 */
data class Semester(val name: String, val courses: List<Course>) {
    /**
     * The total amount of credits in this semester or group of courses.
     */
    val credits: Int
        get() = courses.sumOf { it.credits }

    /**
     * The total amount of points of honor achieved.
     */
    val pointsOfHonor: Int
        get() = courses.sumOf { it.pointsOfHonor }

    /**
     * Percentage of points of honor achieved.
     */
    val pointsOfHonorPercent: Double
        get() = pointsOfHonor.toDouble() / (credits * 4).toDouble() * 100

    /**
     * The gpa achieved in this semester or group of courses.
     */
    val gpa: Double
        get() = pointsOfHonor.toDouble() / credits.toDouble()
}
