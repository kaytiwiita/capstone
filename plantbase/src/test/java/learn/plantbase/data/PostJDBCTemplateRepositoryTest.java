package learn.plantbase.data;

import learn.plantbase.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostJDBCTemplateRepositoryTest {

    final static LocalDateTime DATE_TIME_POSTED = LocalDateTime.now();

    @Autowired
    PostJDBCTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<Post> all = repository.findAll();
        assertTrue(all.size() >= 2);
    }

    @Test
    void shouldFindByIdIfValid() {
        Post post = repository.findById(1);
        assertNotNull(post);
        assertEquals("john_smith", post.getUsername());
        assertNotNull(post.getReplies());
    }

    @Test
    void shouldNotFindByIdIfValid() {
        Post post = repository.findById(6);
        assertNull(post);
    }

    @Test
    void shouldFindAllPostsByValidPlanter() {
        List<Post> posts = repository.findByUsername("john_smith");
        assertNotNull(posts);
        assertTrue(posts.size() >= 1);
    }

    @Test
    void shouldNotFindAnyPostsIfInvalidPlanter() {
        List<Post> posts = repository.findByUsername("test_find_all");
        assertNotNull(posts);
        assertEquals(0, posts.size());
    }

    @Test
    void shouldFindAllPostsByValidPlant() {
        List<Post> posts = repository.findByPlantId(1);
        assertNotNull(posts);
        assertTrue(posts.size() >= 1);
    }

    @Test
    void shouldNotFindAnyPostsIfInvalidPlant() {
        List<Post> posts = repository.findByPlantId(6);
        assertNotNull(posts);
        assertEquals(0, posts.size());
    }

    @Test
    void shouldAddIfValid() {
        Post post = makeNewPost(4);
        Post actual = repository.addPost(post);
        assertEquals(actual, post);

        List<Post> posts = repository.findAll();
        assertTrue(posts.size() >= 2);
    }

    @Test
    void shouldAddIfValidPlantIdIs0() {
        Post post = makeNewPost(5);
        post.setPlantId(0);
        Post actual = repository.addPost(post);
        assertEquals(actual, post);

        List<Post> posts = repository.findAll();
        assertTrue(posts.size() >= 2);
        assertNotNull(actual);
    }

    @Test
    void shouldNotAddIfNull() {
        Post post = repository.addPost(null);
        assertNull(post);
    }

    @Test
    void shouldNotAddIfNullFields() {
        Post post = makeNewPost(5);
        post.setCaption(null);
        Post actual = repository.addPost(post);

        assertNull(actual);
    }

    @Test
    void shouldEditIfValid() {
        Post post = repository.findById(2);
        post.setCaption("new test caption");
        assertTrue(repository.editPost(post));
    }

    @Test
    void shouldEditIfPlantIdIs0() {
        Post post = repository.findById(2);
        post.setPlantId(0);
        post.setCaption("new test caption with 0 plantId");
        assertTrue(repository.editPost(post));
    }

    @Test
    void shouldNotEditIfInvalidPostId() {
        Post post = makeNewPost(6);
        assertFalse(repository.editPost(post));
    }

    @Test
    void shouldNotEditIfNullFields() {
        Post post = repository.findById(2);
        post.setCaption(null);
        assertFalse(repository.editPost(post));
    }

    @Test
    void shouldNotEditIfNull() {
        assertFalse(repository.editPost(null));
    }

    @Test
    void shouldDeleteIfValidId() {
        assertTrue(repository.deletePost(3));
    }

    @Test
    void shouldNotDeleteIfInvalidId() {
        assertFalse(repository.deletePost(6));
    }

    private Post makeNewPost(int postId) {
        Post post = new Post();
        post.setPostId(postId);
        post.setUsername("kaytiwiita");
        post.setGardenId(1);
        post.setPlantId(1);
        post.setCaption("test caption");
        post.setPhoto("testPhoto.png");
        post.setDatetimePosted(DATE_TIME_POSTED);
        post.setLikeCount(0);
        return post;
    }
}