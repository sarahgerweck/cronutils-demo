import java.time._
import scala.compat.java8.OptionConverters._

import org.log4s._

/* Imports for CronUtils */
import com.cronutils._
import parser._
import model._
import definition._
import time._

object Cron extends App {
  private[this] val logger = getLogger

  lazy val crons = Map(
    "every minute" -> "0 * * * * ?",
    "noon every Monday" -> "0 0 12 ? * MON",
    "6 am, first Sunday each month" -> "0 0 6 ? * SUN#1"
  )
  lazy val offsets = Map[String, ZonedDateTime => ZonedDateTime](
    "now" -> identity,
    "three hours ago" -> (_.minusHours(3)),
    "45 days ago" -> (_.minusDays(45))
  )

  lazy val parser = {
    val cd = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ)
    new CronParser(cd)
  }

  showCombinations()

  def showCombinations() {
    val now = ZonedDateTime.now(ZoneOffset.UTC)
    for {
      (cronName, cronExpr) <- crons
      job = parser.parse(cronExpr)
      et = ExecutionTime.forCron(job)
      (offsetName, offset) <- offsets
    } {
      val targetOption = et.nextExecution(offset(now)).asScala
      val target = targetOption.map(_.toString).getOrElse("none")
      logger.info(s"""As of $offsetName, the next execution time for "$cronName" is $target""")
    }
  }
}
