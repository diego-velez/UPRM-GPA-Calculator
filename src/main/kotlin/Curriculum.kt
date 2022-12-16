import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File


@Serializable
/**
 * This class represents an entire curriculum taken.
 * Each curriculum has a list of semesters and a number of credits needed to graduate.
 *
 * @param semesters The lists of semesters.
 * @param creditsNeeded The amount of credits needed to graduate.
 */
data class Curriculum(val semesters: List<Semester>, @SerialName("credits") val creditsNeeded: Int) {

    /**
     * The gpa achieved in the curriculum.
     */
    val gpa: Double
        get() = pointsOfHonor.toDouble() / creditsTaken.toDouble()

    /**
     * The amount of credits taken with the semesters in this curriculum.
     */
    val creditsTaken: Int
        get() = semesters.sumOf { it.credits }

    /**
     * The amount of points of honor achieved with the semesters in this curriculum.
     */
    val pointsOfHonor: Int
        get() = semesters.sumOf { it.pointsOfHonor }

    /**
     * All the concentration courses, in the form of a semester, taken in this curriculum.
     */
    val concentrationCourses: Semester
        get() = Semester(
            "Concentration Courses",
            semesters.flatMap { semester -> semester.courses.filter { it.isConcentration } })

    /**
     * All the non-concentration courses, in the form of a semester, taken in this curriculum.
     */
    val nonConcentrationCourses: Semester
        get() = Semester(
            "Non-Concentration Courses",
            semesters.flatMap { semester -> semester.courses.filterNot { it.isConcentration } })

    companion object {
        /**
         * Builds a [Curriculum] from the JSON file at the path passed.
         * Check the README for the format of the file.
         *
         * @param path The path to the JSON file with the entire curriculum.
         * @return A [Curriculum] object constructed from the JSON file passed.
         */
        fun fromJson(path: String): Curriculum {
            return fromJson(File(path))
        }

        @OptIn(ExperimentalSerializationApi::class)
        /**
         * Builds a [Curriculum] from the JSON file passed.
         * Check the README for the format of the file.
         *
         * @param jsonFile The JSON file with the entire curriculum.
         * @return A [Curriculum] object constructed from the JSON file passed.
         */
        fun fromJson(jsonFile: File): Curriculum {
            return Json.decodeFromStream(jsonFile.inputStream())
        }
    }
}