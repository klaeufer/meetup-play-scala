import com.github.nscala_time.time.Imports._

package object models {

  case class Event(
      time: Long,
      duration: Long,
      name: String,
      group: Group
  )

  case class Group(
      name: String,
      id: Int,
      urlname: String
  )

  case class Effort(
      from: DateTime,
      to: DateTime,
      duration: Duration
  )

  object Effort {
    def apply(events: Seq[Event], interval: Interval): Effort = {
      val eventsDuringInterval = events filter { event => interval.contains(event.time) }
      val durationMillis = eventsDuringInterval.map { _.duration }.sum
      Effort(interval.start, interval.end, Duration.millis(durationMillis))
    }
  }
}
