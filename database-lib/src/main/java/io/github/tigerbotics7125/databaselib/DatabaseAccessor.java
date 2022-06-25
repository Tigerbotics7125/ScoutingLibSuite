package io.github.tigerbotics7125.databaselib;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Deals with the tediousness of accessing the lowest aspect of the database.
 *
 * @author Jeff Morris
 */
public class DatabaseAccessor {

  private static final Logger logger = LogManager.getLogger(DatabaseAccessor.class);
  private static final DatabaseAccessor instance = new DatabaseAccessor();

  private DatabaseAccessor() {}

  public static DatabaseAccessor getInstance() {
    return instance;
  }

  // default mongo instance at localhost
  private String uri = "mongodb://localhost:27017";
  private MongoClient client = MongoClients.create(uri);

  private static final CodecProvider pojoCodecProvider =
      PojoCodecProvider.builder().register("io.github.tigerbotics7125.databaselib.pojos").build();
  private static final CodecRegistry pojoCodecRegistry =
      fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

  /**
   * changes the URI to use, then reconnects the client.
   *
   * @param uri The uri to use.
   */
  public void setUri(String uri) {
    this.uri = uri;
    refreshClient();
    logger.info(String.format("MongoDB uri set to `%s`.", uri));
  }

  /**
   * Reconnects the client using the currently set uri.
   *
   * @see DatabaseAccessor#setUri(String uri)
   */
  public void refreshClient() {
    client = MongoClients.create(uri);
    logger.info("MongoDB client refreshed.");
  }

  /** @return The client. */
  public MongoClient getClient() {
    return client;
  }

  /**
   * Retrieves a database.
   *
   * @param databaseName The database to retrieve.
   * @return The database if {@link com.mongodb.MongoNamespace#checkDatabaseNameValidity(String)} is
   *     valid, null otherwise.
   */
  public MongoDatabase getDatabase(String databaseName) {
    MongoDatabase retVal;
    try {
      retVal = client.getDatabase(databaseName);
      logger.info(String.format("Accessed database `%s`.", databaseName));
      return retVal;
    } catch (IllegalArgumentException illArgEx) {
      logger.error(String.format("Unable to access database `%s` because:", databaseName));
      logger.error(illArgEx.getMessage());
      return null;
    }
  }

  /**
   * Retrieves a collection.
   *
   * @param databaseName The database to retrieve from.
   * @param collectionName The collection to retrieve.
   * @return The collection if {@link
   *     com.mongodb.MongoNamespace#checkCollectionNameValidity(String)} is valid, null otherwise.
   * @see DatabaseAccessor#getDatabase(String)
   */
  public MongoCollection<Document> getCollection(String databaseName, String collectionName) {
    MongoCollection<Document> retVal;
    try {
      retVal = getDatabase(databaseName).getCollection(collectionName);
      logger.info(String.format("Accessed collection `%s`.", collectionName));
      return retVal;
    } catch (IllegalArgumentException illArgEx) {
      logger.error(String.format("Unable to access collection `%s` because:", collectionName));
      logger.error(illArgEx.getMessage());
      return null;
    }
  }

  /**
   * Retrieve a collection with specific documents.
   *
   * @param databaseName The database to retrieve from.
   * @param collectionName The collection to retrieve.
   * @param documentClass The document class to specify.
   * @return The collection.
   * @param <TDocument> The type of document to apply to the collection.
   * @see DatabaseAccessor#getCollection(String, String)
   */
  public <TDocument> MongoCollection<TDocument> getCollection(
      String databaseName, String collectionName, Class<TDocument> documentClass) {
    return getCollection(databaseName, collectionName)
        .withCodecRegistry(pojoCodecRegistry)
        .withDocumentClass(documentClass);
  }
}
