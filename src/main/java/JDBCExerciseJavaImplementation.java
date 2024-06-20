import de.hpi.dbs1.ChosenImplementation;
import de.hpi.dbs1.ConnectionConfig;
import de.hpi.dbs1.JDBCExercise;
import de.hpi.dbs1.entities.Actor;
import de.hpi.dbs1.entities.Movie;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@ChosenImplementation(true)
public class JDBCExerciseJavaImplementation implements JDBCExercise {

	Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public Connection createConnection(@NotNull ConnectionConfig config) throws SQLException {
		final String URL = "jdbc:postgresql://"+ config.getHost() + ":" +config.getPort()+"/"+config.getDatabase();
		return DriverManager.getConnection(URL, config.getUsername(), config.getPassword());
	}

	@Override
	public List<Movie> queryMovies(
		@NotNull Connection connection,
		@NotNull String keywords
	) throws SQLException {
		logger.info(keywords);
		List<Movie> movies = new ArrayList<>();
		Statement titleStmt = connection.createStatement();
		ResultSet titleSet = titleStmt.executeQuery("SELECT * FROM tmovies WHERE \"primaryTitle\" LIKE '%" + keywords + "%' ORDER BY \"primaryTitle\" ASC, \"startYear\" ASC;");

		while(titleSet.next()) {
			var myMovie = new Movie(titleSet.getString("tconst"), titleSet.getString("primaryTitle"), titleSet.getInt("startYear"), Set.of(titleSet.getString("genres")));
			Statement actorStmt = connection.createStatement();
			String tconst = titleSet.getString("tconst");
			ResultSet actorSet = actorStmt.executeQuery("SELECT n.primaryname FROM tprincipals p, nbasics n WHERE \"tconst\" = '" + tconst + "' AND n.nconst = p.nconst AND (p.category = 'actress' OR p.category = 'actor') ORDER BY n.primaryname ASC;");
			while (actorSet.next()) {
				myMovie.actorNames.add(actorSet.getString("primaryname"));
			}

			movies.add(myMovie);
		}



		return movies;


		//myMovie.actorNames.add("Myself");


	}

	@Override
	public List<Actor> queryActors(
		@NotNull Connection connection,
		@NotNull String keywords
	) throws SQLException {
		logger.info(keywords);
		List<Actor> actors = new ArrayList<>();

		throw new UnsupportedOperationException("Not yet implemented");
	}
}
