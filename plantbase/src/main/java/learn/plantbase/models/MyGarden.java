package learn.plantbase.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class MyGarden {
    @Getter
    @Setter
    private int myGardenId;

    @Getter
    @Setter
    @NotBlank(message = "Garden name is required.")
    @NotNull
    @Size(max = 50, message = "Garden name cannot be greater than 50 characters.")
    private String gardenName;

    @Getter
    @Setter
    @NotNull(message = "Username is required.")
    @NotBlank(message = "Username is required.")
    @Size(max = 25, message = "Username cannot be greater than 25 characters.")
    private String username;

    @Getter
    @Setter
    @Size(max = 100, message = "Bio cannot be greater than 50 characters.")
    private String bio;

    @Getter
    @Setter
    @Size(max = 1000, message = "Photo cannot be greater than 1000 characters.")
    private String photo;

    @Setter
    private List<Plant> plants = new ArrayList<>();

    public List<Plant> getPlants() { return new ArrayList<>(plants); }
}
