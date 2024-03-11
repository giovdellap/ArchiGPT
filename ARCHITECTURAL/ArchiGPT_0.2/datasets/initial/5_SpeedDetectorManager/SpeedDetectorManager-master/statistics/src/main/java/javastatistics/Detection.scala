package javastatistics
import it.uniroma1.commons.queue.enums.{CarType, FuelType, RoadType}

class Detection(val id: Long, val speedCameraId: Long, val speedValue: Int,
                val licensePlate: String, val carType: CarType, val fuelType: FuelType,
                val region: String, val roadType: RoadType, val overcameLimit: Boolean)
