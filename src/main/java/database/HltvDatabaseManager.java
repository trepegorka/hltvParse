package database;

import general.logics.AdvantageGenerator;
import interfaces.IHltvDatabase;
import org.apache.commons.math3.util.Precision;
import resultMatches.MatchResult;

import java.sql.*;

public class HltvDatabaseManager implements IHltvDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/myhltv?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "2714OrgD2.";

    private static final String INSERT_NEW = "INSERT INTO hltv (KDRatioAttitude,headshotAttitude,damagePerRoundAttitude,assistsPerRoundAttitude,impactAttitude,kastAttitude,openingKillRatioAttitude,rating3mAttitude,ratingVStop5Attitude,ratingVStop10Attitude,ratingVStop20Attitude,ratingVStop30Attitude,ratingVStop50Attitude,totalKillsAttitude,mapsPlayedAttitude,rankingDifference,winner) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private final AdvantageGenerator advantageGenerator;
    private final MatchResult matchResult;

    private Connection connection;


    public HltvDatabaseManager(AdvantageGenerator advantageGenerator, MatchResult matchResult) {
        this.matchResult = matchResult;
        this.advantageGenerator = advantageGenerator;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillLine() throws Exception {
        PreparedStatement statement = connection.prepareStatement(INSERT_NEW, Statement.RETURN_GENERATED_KEYS);
        statement.setDouble(1, Precision.round(advantageGenerator.KDRatioAttitude(), 3));
        statement.setDouble(2, Precision.round(advantageGenerator.headshotAttitude(), 3));
        statement.setDouble(3, Precision.round(advantageGenerator.damagePerRoundAttitude(), 3));
        statement.setDouble(4, Precision.round(advantageGenerator.assistsPerRoundAttitude(), 3));
        statement.setDouble(5, Precision.round(advantageGenerator.impactAttitude(), 3));
        statement.setDouble(6, Precision.round(advantageGenerator.kastAttitude(), 3));
        statement.setDouble(7, Precision.round(advantageGenerator.openingKillRatioAttitude(), 3));
        statement.setDouble(8, Precision.round(advantageGenerator.rating3mAttitude(), 3));
        statement.setDouble(9, Precision.round(advantageGenerator.ratingVStop5Attitude(), 3));
        statement.setDouble(10, Precision.round(advantageGenerator.ratingVStop10Attitude(), 3));
        statement.setDouble(11, Precision.round(advantageGenerator.ratingVStop20Attitude(), 3));
        statement.setDouble(12, Precision.round(advantageGenerator.ratingVStop30Attitude(), 3));
        statement.setDouble(13, Precision.round(advantageGenerator.ratingVStop50Attitude(), 3));
        statement.setDouble(14, Precision.round(advantageGenerator.totalKillsAttitude(), 3));
        statement.setDouble(15, Precision.round(advantageGenerator.mapsPlayedAttitude(), 3));
        statement.setInt(16, advantageGenerator.rankingDifference());
        statement.setInt(17, matchResult.winner());
        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    /**SET ALLL RANKING DIFF. BEFORE *(-1)  / winner -1 **/
    public static void main(String[] args) throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/myhltv?serverTimezone=UTC";
        String USERNAME = "root";
        String PASSWORD = "2714OrgD2.";

        String UPDATE_NEW = "UPDATE hltv SET winner = ? WHERE id = ?";
        String SELECT = "SELECT winner FROM hltv WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement;
            int before = 0;
            ResultSet resultSet;

            for (int id = 1; id < 2119; id++) {
                preparedStatement = connection.prepareStatement(SELECT);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    before = resultSet.getInt("winner");
                }

                preparedStatement = connection.prepareStatement(UPDATE_NEW);
                preparedStatement.setInt(1, before -1);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(SELECT);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if(before == resultSet.getInt("winner")){
                        throw new Exception();
                    } else System.out.println("Success №" + id);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
