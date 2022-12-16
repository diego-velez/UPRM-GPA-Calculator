
rootProject.name = "GPA-Calculator"

// The Tabletery project must be in the same root folder as this one
include(":Tabletery")
project(":Tabletery").projectDir = File(rootDir.parentFile, "Tabletery")
