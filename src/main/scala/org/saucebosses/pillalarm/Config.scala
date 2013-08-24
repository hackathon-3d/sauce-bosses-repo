package org.saucebosses.pillalarm

import com.mongodb.casbah.Imports._

object Config {
	lazy val mongoClient = MongoClient("ec2-50-16-178-223.compute-1.amazonaws.com")
}