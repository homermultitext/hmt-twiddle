import edu.holycross.shot.cite._
import edu.holycross.shot.orca._

import scala.io.Source

val sourceLines = Source.fromFile("data/critsignorca.tsv").getLines.toVector

val header = sourceLines(0)
val data = sourceLines.tail

for (ln <- data) {
  try {
    OrcaCollection(header + "\n" + ln)
  } catch {
    case _ : Throwable => println("\n\nFailed on input " + ln + "\n\n")
  }
}
