package cubscout.utilities;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cubscout.backend.pojos.Team;
import org.bson.conversions.Bson;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class HighLevelDatabaseUtil {

  /**
   * @param dbName The name of the database.
   * @return The number of collections in a database.
   */
  public static int getTotalCollections(String dbName) {
    MongoDatabase db = LowLevelDatabaseUtil.getDatabase(dbName);
    // counts number of collections in database
    return db.listCollectionNames().into(new ArrayList<>()).size();
  }

  /**
   * @param <T> The type of the collection.
   * @param dbName The name of the database.
   * @param cName The name of the collection.
   * @param clazz The type of the collection.
   * @return The number of documents in a collection.
   */
  public static <T> long getTotalDocuments(String dbName, String cName, Class<T> clazz) {
    MongoCollection<T> collection = LowLevelDatabaseUtil.getCollection(dbName, cName, clazz);
    return collection.countDocuments();
  }

  /** @return the current year's collection. */
  public static MongoCollection<Team> getYearlyCollection() {
    return LowLevelDatabaseUtil.getCollection("cub-scout", "RapidReact", Team.class);
  }

  public static void insertTeam(Team team) {
    var collection = getYearlyCollection();
    Bson filter = and(eq("number", team.getNumber()), eq("scouterId", team.getScouterId()));
    if (collection.find(filter).first() != null) {
      collection.replaceOne(filter, team);
    } else {
      collection.insertOne(team);
    }
  }
}
