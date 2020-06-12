/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.newardassociates.pptbuilder

import kotlinx.cli.*
import java.io.File
import java.io.FileInputStream
import java.util.*

class App(val parser : Parser, val processor : Processor) {
    fun run(file : File) {
        val preso = parser.parse(file)
        processor.process(preso)
    }
}

fun main(args: Array<String>) {
    val properties = Properties()
    properties["author"] = "Fred Flintstone"
    properties["affiliation"] = "Gravel Mining, Inc"
    properties["contact.email"] = "fred@bedrock.gov"
    properties["contact.twitter"] = "@fredflintstone"
    properties["contact.blog"] = "http://fred.blogs.com"
    properties["contact.linkedin"] = "http://www.linkedin.com/profiles/fred"

    if (File("~/.pptbuilder.properties").exists())
        properties.load(FileInputStream("~/.pptbuilder.properties"))


    val cliParser = ArgParser("pptbuilder")
    val input by cliParser.option(ArgType.String, fullName = "input", shortName = "i",
            description = "Input file") //.required()
    val output by cliParser.option(ArgType.String, fullName = "output", shortName = "o",
            description = "Output file name").default("")
    val format by cliParser.option(ArgType.String, fullName = "format", shortName = "f",
            description = "Output format to use").default("pptx")
    cliParser.parse(args)

    val inputFile = input.toString()
    val outputFile =
            (if (output.toString() != "") output.toString() else inputFile.substringBeforeLast('.')) +
            "." + format.toString()

    println("Parsing ${inputFile} to ${outputFile}...")

    val app = App(Parser(properties), PPTXProcessor(Processor.Options(outputFilename = "test")))
    app.run(File("./slidesamples/xmlmd/Testing.xmlmd"))

    /*

    pptbuilder:
    --params <file>: use <file> for the parameters
    --debug: turn on copious debugging
    --input <file>: use <file>.xmlmd as the source file
    --output <file>: write output to <file>.<format>
    --format <processor>: use <processor> to generate the output
    --noTitleSlide: don't create the first title slide
    --template <file>: use <template>.<format> as the starting point and merge in new content (PPTX only?)


    val input by parser.option(ArgType.String, fullName = "input", shortName = "i",
            description = "Input file").required()
    val output by parser.option(ArgType.String, fullName = "output", shortName = "o",
            description = "Output file name").default("")
    val debug by parser.option(ArgType.Boolean, fullName = "debug", shortName = "d",
            description="Turn on debug/verbose mode").default(false)
    parser.parse(args)
     */

    // These two as arguments?
    //val format by parser.option(ArgType.Choice(enumValues(OutputFormat)), fullName = "format", shortName = "f",
    //        description="Output format type").default("pptx").multiple()

}
