Compile the code into Uber jar
-----------------------------------------------

    sbt assembly

The Jar will be created as ingestion-spark-processor-dataset-assembly-1.1.0.jar at [project-root-dir]/target/scala-2.11/

Run the following commands to setup the working directories and files
----------------------------------------------------------------------

    cd <project-root-directory>
    mkdir ./src
    mkdir ./src/data
    mkdir ./src/data/input
    mkdir ./src/data/output
    mkdir ./src/data/trap
    mkdir ./src/data/archive
    mkdir ./src/data/state-store
    touch src/test/data/state-store/data/part-00000
    touch src/test/data/state-store/data/_SUCCESS
    mv ./test-data/sample-data-csv-file1.csv ./src/data/input

Run the jar
-----------------------------------------------

    spark-submit \
    --master local \
    --class com.datalogs.dataset.SampleDatasetCsvProcessor \
    /Users/joychak/joy/workspace/repo/data-ingestion/spark-processor-dataset/target/scala-2.11/ingestion-spark-processor-dataset-assembly-1.1.0.jar \
    --input-dir /Users/joychak/joy/workspace/repo/data-ingestion/src/test/data/input \
    --output-dir /Users/joychak/joy/workspace/repo/data-ingestion/src/test/data/output \
    --archive-dir /Users/joychak/joy/workspace/repo/data-ingestion/src/test/data/archive \
    --trap-dir /Users/joychak/joy/workspace/repo/data-ingestion/src/test/data/trap \
    --state-store-dir /Users/joychak/joy/workspace/repo/data-ingestion/src/test/data/state-store \
    --batch-id 20181231080000 \
    --duration 525600 \
    --prepare-n-days 365 \
    --dataSourceName CSV-DATA

The command line parameters to run these program are -

    1.   "**--master**" is required and can be set at "**local**" for local installation of spark or "**yarn**" (e.g. --master yarn)

    3.   "**--class**" is required to specify the fully-qualified-name of the "main" method in JAR and the location of the ingestion-spark-processor-dataset-assembly-1.1.0.jar file.

    4.   "**--input-dir**" is required to specify the location of input files.

    5.   "**--output-dir**" is required to specify the location of the output parquet files.

    6.   "**--archive-dir**" is required to specify the location of the archive folder of the input files.
    
    7.   "**--trap-dir**" is required to specify the location of the trap file location.
    
    8.   "**--state-store-dir**" is required to specify the location of state-store which stores the file names to check and avoid processing duplicate files.
    
    9.   "**--batch-id**" is required to specify that is in the format of YYYYMMDDHHMMSS and needs to be the end time of file selection for processing.
    
    10.  "**--duration**" is optional to specify the batch duration. The start time of the file selection will be calculated using this value. The default value is 120 min.
    
    11.  "**--prepare-n-days**" is optional to specify how many days back data can be expected. The default value is 120 days
    
    12.  "**--dataSourceName**" is optional to specify the name of the data source.
    

Run the following command at "spark-shell" to view the ingested parquet data
-----------------------------------------------------------------------------

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val df = sqlContext.read.parquet("/Users/joychak/joy/workspace/repo/data-ingestion/src/test/data/output/eventDate=2018-04-02/batchId=20181231080000/*")
    df.show()
