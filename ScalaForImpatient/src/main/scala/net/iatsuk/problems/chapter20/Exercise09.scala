package net.iatsuk.problems.chapter20

import java.io.File
import java.nio.file.{Files, Path}
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

import scala.actors.Actor
import scala.collection.JavaConverters._
import scala.io.Source
import scala.util.matching.Regex

object Exercise09 extends App {

  case class Task(path: Path, regExp: Regex, composer: Actor, counter: Counter)

  class DirActor extends Actor {
    override def act(): Unit = {
      react {
        case task: Task => Files.list(task.path).collect(Collectors.toList()).asScala
          .foreach {
            case path if Files.isDirectory(path) => new DirActor().start() ! task.copy(path = path)
            case path if Files.isRegularFile(path) => new FileActor().start() ! task.copy(path = path)
          }
      }
    }
  }

  class FileActor extends Actor {
    override def act(): Unit = {
      react {
        case task: Task => try {
          val count = Source.fromFile(task.path.toFile).getLines().map(task.regExp.findAllIn(_).size).sum
          task.counter.count += count
          task.composer ! count
        } catch {
          case _: Throwable => None
        }
      }
    }
  }

  class ComposeActor extends Actor {
    private val counter = new Counter

    def result: Int = counter.count

    override def act(): Unit = {
      while (true) {
        receive {
          case matches: Int => counter.count += matches
        }
      }
    }
  }

  class Counter {
    var count = 0
  }

  Actor.actor {
    val dir = new File(".")
    val regExp = "e0[1|2]".r
    val composer = new ComposeActor().start().asInstanceOf[ComposeActor]
    val commonCounter = new Counter

    new DirActor().start() ! Task(dir.toPath, regExp, composer, commonCounter)

    TimeUnit.MILLISECONDS.sleep(300)
    printf("Total matches: %d%nCommont counter value: %d%n", composer.result, commonCounter.count)
    System.exit(0)
  }
}
