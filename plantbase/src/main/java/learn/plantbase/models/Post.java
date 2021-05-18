package learn.plantbase.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    @Getter
    @Min(value = 0, message = "Id must be greater than or equal to 0.")
    private int postId;

    @Getter
    @Setter
    @Min(value = 0, message = "Id must be greater than or equal to 0.")
    private int userId;

    @Getter
    @Setter
    @NotNull
    @Size(max=250, message = "file name is too big.")
    private String caption;

    @Getter
    @Setter
    @Size(max=1000, message = "file name is too big.")
    private String photo;

    @Getter
    @Setter
    @NotNull
    @Past
    private LocalDateTime datetimePosted;

    @Getter
    @Setter
    @Min(value = 0, message = "Id must be greater than or equal to 0.")
    private int likecount;

    @Getter
    @Setter
    @Min(value = 0, message = "Id must be greater than or equal to 0.")
    private int plantId;

    @Getter
    @Setter
    private List<Reply> replies = new ArrayList<>();

}
