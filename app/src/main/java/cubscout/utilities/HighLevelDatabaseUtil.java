package cubscout.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import cubscout.backend.pojos.Team;

public class HighLevelDatabaseUtil {

  /**
   * @param dbName The name of the database.
   * @return The number of collections in a database.
   */
  public static int getTotalCollections(String dbName) {
    MongoDatabase db = LowLevelDatabaseUtil.getDatabase(dbName);
    // counts number of collections in database
    int totalEntries = db.listCollectionNames().into(new ArrayList<String>()).size();

    return totalEntries;
  }

  /**
   * @param <T> The type of the collection.
   * @param dbName The name of the database.
   * @param cName The name of the collection.
   * @param clazz The type of the collection.
   * @return The number of documents in a collection.
   */
  public static <T> long getTotalDocuments(
      String dbName, String cName, Class<T> clazz) {
    MongoCollection<T> collection = LowLevelDatabaseUtil.getCollection(dbName, cName, clazz);
    return collection.countDocuments();
  }

  /** @return the current year's collection. */
  public static MongoCollection<Team> getYearlyCollection() {
    String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    return LowLevelDatabaseUtil.getCollection("cub-scout", year, Team.class);
  }

  public static InsertOneResult insertTeam(Team team) {
    return getYearlyCollection().insertOne(team);
  }
}
