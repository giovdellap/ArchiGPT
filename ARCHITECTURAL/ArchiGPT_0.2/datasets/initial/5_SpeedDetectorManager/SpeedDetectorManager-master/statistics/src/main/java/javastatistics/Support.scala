package javastatistics

import it.uniroma1.commons.queue.enums.{CarType, FuelType, RoadType}
import scala.collection.immutable
import scala.List
import io.circe.parser.decode
object Support {
  def detectionFromString(v: List[String]): Detection = {
    var ct = CarType.SUV
    if (v(4).equals("MICRO"))
      ct = CarType.MICRO
    else if (v(4).equals("VAN"))
      ct = CarType.VAN
    else if (v(4).equals("COUPE"))
      ct = CarType.COUPE
    else if (v(4).equals("SUPERCAR"))
      ct = CarType.SUPERCAR
    else if (v(4).equals("CAMPER"))
      ct = CarType.CAMPER
    else if (v(4).equals("TRUCK"))
      ct = CarType.TRUCK

    var ft = FuelType.GASOLINE
    if (v(5).equals("DIESEL"))
      ft = FuelType.DIESEL
    else if (v(5).equals("LPG"))
      ft = FuelType.LPG
    else if (v(5).equals("ETHANOL"))
      ft = FuelType.ETHANOL
    else if (v(5).equals("HYBRID"))
      ft = FuelType.HYBRID
    else if (v(5).equals("FULL_ELECTRIC"))
      ft = FuelType.FULL_ELECTRIC

    var rt = RoadType.CITY_ROAD
    if (v(7).equals("HIGHWAY"))
      rt = RoadType.HIGHWAY
    else if (v(7).equals("THROUGHWAY"))
      rt = RoadType.THROUGHWAY

    new Detection(v(0).toLong, v(1).toLong, v(2).toInt, v(3), ct, ft, v(6), rt, v(8).toBoolean)
  }

  def executeQueries(list: String) = {

    var l: List[Detection] = Nil
    list.split("\n").foreach(t => {
      val out = decode[List[String]](t)
      println(out)
      out match {
        case Left(value) => println("ERRORE")
        case Right(value) => {
          l = Support.detectionFromString(value) :: l
        }
      }
    })

    println("Autovelox più attivo: " + Utils.mostActiveSpeedCamera(l))
    println("Velocità più alta raggiunta: " + Utils.highestSpeed(l))
    println("Regione più trafficata: " + Utils.busiestRegion(l))
    println("Percentuale di veicoli eco-friendly (elettrici o ibridi): " + Utils.ecoFriendlyPercentage(l) * 100 + "%")
    println("Regioni con più multe: " + Utils.mostCriminalRegion(l))
    val hasc = Utils.highestAverageSpeedCamera(l)
    println("Autovelox con la media di velocità registrate più alta: " + hasc._1 + ", " + hasc._2)
    val har = Utils.highestAverageRegion(l)
    println("Regione con la percentuale più alta di rilevazioni sopra il limite di velocità: " + har._1 + ", " + har._2 * 100 + "%")
    println("Alimentazione più diffusa: " + Utils.mostUsedFuel(l))
    println("Veicolo più diffuso: " + Utils.mostUsedCarType(l))
    val hacr = Utils.highestAverageCriminalRoad(l)
    println("Tipo di strada con percentuale più alta di rilevazioni sopra il limite: " + hacr._1 + ", " + hacr._2 * 100 + "%")
  }

}
