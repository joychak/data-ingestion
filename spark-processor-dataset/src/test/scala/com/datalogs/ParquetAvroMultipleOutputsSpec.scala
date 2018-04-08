//
//package com.datalogs
//
//import com.datalogs.parsers.{SampleDatasetEnricher, SampleDatasetParser}
//import org.apache.hadoop.fs.Path
//import org.apache.spark.{SparkConf, SparkContext}
//import org.specs2.mutable._
//
//class ParquetAvroMultipleOutputsSpec extends Specification {
//  val OutputDir = new Path("dmz/processing/target/test-output-dir")
//
//  "A ParquetAvroMultipleOutputsSpec" should {
//    "create multiple parquet files" in {
//      val sc = SparkContext.getOrCreate(new SparkConf().setMaster("local").setAppName("test").set("spark.driver.allowMultipleContexts", "true"))
//      val sink = DmzSink.parquet[UxAlarm](sc, OutputDir, "2018041900", 2 )
//      sink.prepare()
//      val s = """UXTMP009 WintrvFocusChange SN:563288,UUID:5306310,P:5,ClientLocal:07-34-48.666 19-Apr-16 GMT+8-00,Function:NotSet,Security:NotSet,WindowTitle:IB - Main,ElapsedTime:33"""
//      val recordObj = new LineRecord("filename", s, "batchId", Map.empty[String,String])
//      val r = List(recordObj)
//      sc.parallelize(r).map(UxAlarmParser(_)).map(UxAlarmEnricher(_).right.get.transformedForWrite).saveToSink(sink)
//     // sc.parallelize(r).map(SampleDatasetParser(_).right.get.transformedForWrite).saveToSink(sink)
//      sink.cleanUp()
//      success
//    }
//  }
//
//
//}
