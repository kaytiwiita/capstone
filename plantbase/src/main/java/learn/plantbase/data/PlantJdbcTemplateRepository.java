package learn.plantbase.data;

import learn.plantbase.data.mappers.PlantMapper;
import learn.plantbase.data.mappers.PostMapper;
import learn.plantbase.models.Plant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlantJdbcTemplateRepository implements PlantRepository{

    private final JdbcTemplate jdbcTemplate;

    public PlantJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Plant> findAll() {
        final String sql = "select plant_id, my_garden_id, plant_description, photo, plant_name, plant_type, gotcha_date from plant;";
        return jdbcTemplate.query(sql, new PlantMapper());
    }

    @Override
    @Transactional
    public Plant findByPlantId(int plantId) {
        final String sql = "select plant_id, my_garden_id, plant_description, photo, plant_name, plant_type, gotcha_date " +
                "from plant where plant_id = ?";

        Plant plant = jdbcTemplate.query(sql, new PlantMapper(), plantId).stream().findFirst().orElse(null);

        if (plant != null) {
            addPosts(plant);
        }
        return plant;
    }

    @Override
    @Transactional
    public List<Plant> findByMyGardenId(int myGardenId) {
        final String sql = "select plant_id, my_garden_id, plant_description, photo, plant_name, plant_type, gotcha_date " +
                "from plant where my_garden_id = ?";

        List<Plant> plants = jdbcTemplate.query(sql, new PlantMapper(), myGardenId);

        if (plants.size() > 0) {
            for (Plant plant : plants) {
                addPosts(plant);
            }
        }
        return plants;
    }

    @Override
    public Plant addPlant(Plant plant) {
        if (plant == null) {
            return null;
        }

        if (plant.getPlantDescription() == null || plant.getGotchaDate() == null) {
            return null;
        }

        final String sql = "insert into plant (my_garden_id, plant_description, photo, plant_name, plant_type, gotcha_date) " +
                " values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, plant.getMyGardenId());
            ps.setString(2, plant.getPlantDescription());
            ps.setString(3, plant.getPhoto());
            ps.setString(4, plant.getPlantName());
            ps.setString(5, plant.getPlantType());
            ps.setDate(6, Date.valueOf(plant.getGotchaDate()));
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        plant.setPlantId(keyHolder.getKey().intValue());
        return plant;
    }

    @Override
    public boolean editPlant(Plant plant) {
        if (plant.getPlantDescription() == null || plant.getGotchaDate() == null) {
            return false;
        }

        final String sql = "update plant set " +
                "my_garden_id = ?, " +
                "plant_description = ?, " +
                "photo = ?, " +
                "plant_name = ?, " +
                "plant_type = ?, " +
                "gotcha_date = ? " +
                "where plant_id = ?;";

        return jdbcTemplate.update(sql,
                plant.getMyGardenId(),
                plant.getPlantDescription(),
                plant.getPhoto(),
                plant.getPlantName(),
                plant.getPlantType(),
                plant.getGotchaDate(),
                plant.getPlantId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int plantId) {
        jdbcTemplate.update("set sql_safe_updates = 0;");
        // delete replies
        final String sql = "delete r from reply r " +
                "inner join post p on r.post_id = p.post_id " +
                "inner join plant pl on p.plant_id = pl.plant_id " +
                "where pl.plant_id = ?;";
        jdbcTemplate.update(sql, plantId);
        jdbcTemplate.update("set sql_safe_updates = 1;");

        jdbcTemplate.update("delete from post where plant_id = ?;", plantId);
        return jdbcTemplate.update("delete from plant where plant_id = ?;", plantId) > 0;
    }

    //commented out until PostMapper is merged
    private void addPosts(Plant plant) {
        final String sql = "select post_id, username, plant_id, garden_id, caption, photo, datetime_posted, like_count " +
                "from post " +
                "where plant_id = ?;";
        var posts = jdbcTemplate.query(sql, new PostMapper(), plant.getPlantId());
        plant.setPosts(posts);
    }
}
