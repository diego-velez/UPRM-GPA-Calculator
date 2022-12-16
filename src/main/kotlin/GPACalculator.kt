import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * CHANGE THIS VARIABLE TO THE PATH OF YOUR CURRICULUM JSON
 */
private const val CURRICULUM_JSON_PATH = "src/main/resources/curriculum.json"

/**
 * This class formats [Curriculum], [Semester] and [Course] objects in a way that makes them able to be printed to
 * the console.
 */
object GPACalculator {
    @JvmStatic
    fun main(args: Array<String>) {
        val curriculum = Curriculum.fromJson(CURRICULUM_JSON_PATH)
        printCurriculum(curriculum)
    }

    private fun printCurriculum(curriculum: Curriculum) {
        val header = listOf("Code", "Name", "Credits", "Grade")

        // Makes the table for each semester
        val semesterController = TableFormatter(tablesHorizontally = 2, paddingBetweenTables = 10)
        curriculum.semesters
            .forEach { semester ->
                semester.courses
                    .map { listOf(it.code, it.name, it.credits.toString(), it.grade) }
                    .let { courses ->
                        val footer = mutableListOf("Total").apply {
                            add("GPA: ${semester.gpa.round(2)}")
                            add(semester.credits.toString())
                            add(semester.pointsOfHonor())
                        }

                        Table.TableBuilder()
                            .addTitle(semester.name)
                            .addHeader(header)
                            .addData(courses)
                            .addFooter(footer)
                            .build()
                            .let { table -> semesterController.tables.add(table) }
                    }
            }

        val otherTablesController = TableFormatter(2, false, 10)
        if (curriculum.concentrationCourses.courses.isNotEmpty()) {
            var footer = listOf(
                "Total",
                "GPA: ${curriculum.concentrationCourses.gpa}",
                curriculum.concentrationCourses.credits.toString(),
                curriculum.concentrationCourses.pointsOfHonor()
            )

            val concentrationCourses = curriculum.concentrationCourses.courses
                .map { listOf(it.code, it.name, it.credits.toString(), it.grade) }

            Table.TableBuilder()
                .addTitle(curriculum.concentrationCourses.name)
                .addHeader(header)
                .addData(concentrationCourses)
                .addFooter(footer)
                .build()
                .let { table -> otherTablesController.tables.add(table) }

            // Non-concentration classes table
            val nonConcentrationCourses = curriculum.nonConcentrationCourses.courses
                .map { listOf(it.code, it.name, it.credits.toString(), it.grade) }
            footer = listOf(
                "Total",
                "GPA: ${curriculum.nonConcentrationCourses.gpa}",
                curriculum.nonConcentrationCourses.credits.toString(),
                curriculum.nonConcentrationCourses.pointsOfHonor()
            )

            Table.TableBuilder()
                .addTitle(curriculum.nonConcentrationCourses.name)
                .addHeader(header)
                .addData(nonConcentrationCourses)
                .addFooter(footer)
                .build()
                .let { table -> otherTablesController.tables.add(table) }
        }

        // Curriculum total table
        val totalTableHeader = listOf("GPA", "Credits", "Points of Honor")
        val totalTableData = listOf(
            listOf(
                curriculum.gpa.round(2).toString(),
                curriculum.credits(),
                curriculum.pointsOfHonor()
            )
        )

        Table.TableBuilder()
            .addTitle("Total")
            .addHeader(totalTableHeader)
            .addData(totalTableData)
            .build()
            .let { otherTablesController.tables.add(it) }

        println(semesterController.toString())
        println(otherTablesController.toString())
    }

    /**
     * Rounds a [Double] to [decimal] places.
     *
     * @param decimal The amount of decimal places to round the number to.
     * @return The rounded number.
     */
    private fun Double.round(decimal: Int): Double {
        val factor = 10.0.pow(decimal)
        return (this * factor).roundToLong() / factor
    }

    /**
     * Returns a string with the points of honor achieved, the total amount that could have been achieved and
     * the percent achieved.
     */
    private fun Semester.pointsOfHonor(): String {
        return "$pointsOfHonor / ${credits * 4} (${pointsOfHonorPercent.round(2)}%)"
    }

    /**
     * Returns a string with the points of honor achieved, the total amount that could have been achieved and
     * the percent achieved.
     */
    private fun Curriculum.pointsOfHonor(): String {
        val pointsOfHonorPercent = pointsOfHonor.toDouble() / (creditsTaken * 4).toDouble() * 100
        return "$pointsOfHonor / ${creditsTaken * 4} (${pointsOfHonorPercent.round(2)}%)"
    }

    /**
     * Returns a string with the credits taken, the total amount of credits needed to graduate and
     * the percent completed.
     */
    private fun Curriculum.credits(): String {
        val creditsTakenPercent = creditsTaken.toDouble() / (creditsNeeded * 4).toDouble() * 100
        return "$creditsTaken / $creditsNeeded (${creditsTakenPercent.round(2)}%)"
    }
}