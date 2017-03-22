package com.ariap.core

import com.ariap.config.Parser
import com.ariap.ftp.FTPClient
import com.netaporter.uri.Uri.parse

import scala.sys.process._

case class Config(config: String = "config.yml")

object Runner extends App {
  val cmdParser = new scopt.OptionParser[Config]("Aria2P") {
    head("Aria2P", "1.0")

    opt[String]('c', "config").optional().valueName("<file path>").
      action((x, c) => c.copy(config = x)).
      text("Config path")
  }

  val parser = cmdParser.parse(args, Config()) match {
    case Some(args) =>
      new Parser(args.config)
    case None =>
      throw new Exception("Wrong arguments")
  }


  val user = parser.loadUser()
  val aria2 = parser.loadAria2()
  val ftp = parser.loadFtp()
  val download = parser.loadDownload()
  val client = FTPClient()

  def polling(): String = {
    def matching = (e: (String, Long)) => download.query.split(" ").forall(e._1.contains(_))

    list.find(matching) match {
      case Some((name, _)) => s"ftp://${ftp.url}/$name"
      case None =>
        println(s"Nothing is found with the keywords: ${download.query}")
        Thread.sleep((1000 * download.interval).toLong)
        polling()
    }
  }

  println("Start connecting to ftp")
  if ((user.user == null || user.user.trim.isEmpty) && (user.password == null || user.password.trim.isEmpty)) {
    client.connectWithAuth(ftp.url)
  } else {
    client.connectWithAuth(ftp.url, user.user, user.password)
  }
  val list = client.filesWithTime.sortBy(_._2).reverse
  client.disconnect()

  println(s"Queries to run against ftp: ${download.query}")
  val urlToBeDownloaded = polling()

  println(s"Start downloading: $urlToBeDownloaded")

  if ((user.user == null || user.user.trim.isEmpty) && (user.password == null || user.password.trim.isEmpty)) {
    s"${aria2.path} ${aria2.args} ${parse(urlToBeDownloaded).toString} --dir=${download.output}".!
  } else {
    s"${aria2.path} ${aria2.args} --ftp-user=${user.user} --ftp-passwd=${user.password} ${parse(urlToBeDownloaded).toString} --dir=${download.output}".!
  }
}


