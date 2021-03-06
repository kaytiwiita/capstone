package learn.plantbase.data.mappers;

import learn.plantbase.models.Reply;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ReplyMapper implements RowMapper<Reply> {

    @Override
    public Reply mapRow(ResultSet resultSet, int i) throws SQLException {
        Reply reply = new Reply();
        reply.setReplyId(resultSet.getInt("reply_id"));
        reply.setUsername(resultSet.getString("username"));
        reply.setPostId(resultSet.getInt("post_id"));
        reply.setReply(resultSet.getString("reply"));

        Timestamp datetimePosted = resultSet.getTimestamp("datetime_posted");
        LocalDateTime timePosted = datetimePosted.toLocalDateTime();

        int hours = LocalDateTime.now().getHour();
        int hoursUTC = LocalDateTime.now(ZoneOffset.UTC).getHour();
        int difference = hoursUTC - hours;

        reply.setDatetimePosted(timePosted.plusHours(difference));

        reply.setLikeCount(resultSet.getInt("like_count"));

        return reply;
    }
}
