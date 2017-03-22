package com.ariap.config

import java.io.{BufferedInputStream, FileInputStream}

import com.ariap.data.{Aria2, Download, FTP, User}
import org.yaml.snakeyaml.Yaml

import scalaj.collection.Imports._

final class Parser(config: String) {
  val yaml: Yaml = new Yaml()
  val parsedMap = yaml.load(new BufferedInputStream(new FileInputStream(config))).asInstanceOf[java.util.Map[String, String]].asScala

  def loadUser(): User = User(user = parsedMap("user"), password = parsedMap("password"))

  def loadAria2(): Aria2 = Aria2(path = parsedMap("aria2_path"), args = parsedMap("aria2_args"))

  def loadFtp(): FTP = FTP(url = parsedMap("ftp_url"))

  def loadDownload(): Download = Download(query = parsedMap("query"), output = parsedMap("output"), interval = parsedMap("interval").replace("min", "").toDouble * 60)
}
