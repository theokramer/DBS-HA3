package de.hpi.dbs1;

import de.hpi.dbs1.entities.Actor;
import de.hpi.dbs1.entities.Movie;
import org.jetbrains.annotations.NotNull;

import javax.sound.midi.SysexMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;

public interface JDBCExercise {

    /**
     * Creates a connection to the database.
     * See <a href="https://jdbc.postgresql.org/documentation/use/">Postgres JDBC Driver Documentation</a>
     * for examples on how to do this.
     * <p><b>2 Points</b>
     *
     * @param config all relevant connection details (see {@link ConnectionConfig})
     * @return an open connection to the database
     * @throws SQLException if the connection could not be established
     */

	Connection createConnection(@NotNull ConnectionConfig config) throws SQLException;


    /**
	 * Searches the database for movies whose title contains the keywords.
	 * <ul>
	 * <li>Each movie consists of its unique ID (tconst), title, year and genres
	 *     represented by the {@link de.hpi.dbs1.entities.Movie} class.
	 *     <p><b>4 Points</b>
	 * <li>All movies should be ordered by ascending title first,
	 *     ascending year second
	 *     <p><b>2 Points</b>
	 * <li>Each movie should also contain a list of all actors or actresses,
	 *     which played in the movie (if there are any), ordered by ascending name.
	 *     <p>Insert their names into the list {@link Movie#actorNames}.
	 *     <p><b>4 Point</b>
	 * </ul>
	 *
	 * @param connection an open database connection
	 * @param keywords the keywords for which to search
	 * @return a list of all found movies
	 * @throws SQLException if the query fails
	 */
	List<Movie> queryMovies(
		@NotNull Connection connection,
		@NotNull String keywords
	) throws SQLException;

	/**
	 * Searches the database for the 5 most relevant actors/actresses whose title contains the keywords.
	 * <ul>
	 * <li>Each actor/actress consists of its unique ID (nconst) and name
	 *     represented by the {@link Actor} class.
	 *     <p><b>3 Points</b>
	 * <li>An actor/actress is more relevant, if he/she played in more movies. Therefore they should be
	 *     ordered by descending movie count first, ascending name second.
	 *     <p><b>2 Points</b>
	 * <li>Each actor/actress should also contain a list of the last 5 movies in which he/she played in,
	 *     ordered by descending year first (with missing years put last), ascending title second.
	 *     <p>Insert these movie titles into the list {@link Actor#playedIn}.
	 *     <p><b>4 Points</b>
	 * <li>Additionally, each actor/actress should also contain information about the 5 most important
	 *     actors/actresses from all movies both stars shared. The importance is defined as the
	 *     number of movies shared. Therefore we represent this as a map from costar name to
	 *     shared movie count. Its entries should be ordered by descending shared movie count first,
	 *     and ascending costar name second. The insertion order determines the map order for iteration.
	 *     <p>Insert these costars and shared movie counts into the map {@link Actor#costarNameToCount}.
	 *     <p><b>6 Points</b>
	 * </ul>
	 *
	 * @param connection an open database connection
	 * @param keywords the keywords for which to search
	 * @return a list of all found actors
	 * @throws SQLException if the query fails
	 */
	List<Actor> queryActors(
		@NotNull Connection connection,
		@NotNull String keywords
	) throws SQLException;
}
