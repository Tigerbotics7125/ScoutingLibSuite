package cubscout.utilities;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class LowLevelDatabaseUtil {
  private static final Logger logger = LogManager.getLogger(LowLevelDatabaseUtil.class);

  private static final String mUri = "mongodb://localhost:27017";
  private static final MongoClient mClient = MongoClients.create(mUri);
  private static final CodecProvider pojoCodecProvider =
      PojoCodecProvider.builder().register("cubscout.backend.pojos").build();
  private static final CodecRegistry pojoCodecRegistry =
      fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

  public static MongoDatabase getDatabase(String dbName) {
    logger.info(String.format("Attempting to access database '%s'.", dbName));

    MongoDatabase db = mClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);

    logger.info(String.format("Database '%s' accessed", dbName));

    return db;
  }

  /**
   * @param <T> The type of the collection.
   * @param dbName The name of the database.
   * @param cName The name of the collection.
   * @param clazz The type of the collection.
   * @return A collection of type T.
   */
  public static <T> MongoCollection<T> getCollection(String dbName, String cName, Class<T> clazz) {
    logger.info(
        String.format("Attempting to access collection '%s' in database '%s'.", cName, dbName));

    MongoDatabase db = getDatabase(dbName);
    MongoCollection<T> col = db.getCollection(cName, clazz);

    logger.info(String.format("Collection '%s' in database '%s' accessed.", cName, dbName));

    return col;
  }
}
