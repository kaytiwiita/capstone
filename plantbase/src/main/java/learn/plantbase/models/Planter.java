package learn.plantbase.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Planter {

    @Getter
    @Setter
    @Min(value = 1, message = "Role id must be at least 1.")
    private int roleId;

    @Getter
    @Setter
    @NotNull(message = "Username is required.")
    @NotBlank(message = "Username is required.")
    @Size(max = 25, message = "Username cannot be greater than 25 characters.")
    private String username;

    @Getter
    @Setter
    @NotNull(message = "First name is required.")
    @NotBlank(message = "First name is required.")
    @Size(max = 25, message = "First name cannot be greater than 25 characters.")
    private String firstName;

    @Getter
    @Setter
    @NotNull
    @NotBlank(message = "Last name is required.")
    @Size(max = 25, message = "Last name cannot be greater than 25 characters.")
    private String lastName;

    @Getter
    @Setter
    @NotNull
    @NotBlank(message = "Email is required.")
    @Size(max = 50, message = "Email cannot be greater than 50 characters.")
    private String email;


    @Getter
    @Setter
    private MyGarden myGarden;



    @Setter
    private List<Post> posts = new ArrayList<>();

    public List<Post> getPosts() {
        return new ArrayList<>(posts);
    }
}
