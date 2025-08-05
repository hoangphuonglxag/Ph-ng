@SpringBootTest
public class ContentTagMapperTest {

    @Autowired
    private ContentTagMapper mapper;

    @Test
    public void testEntityToDto() {
        ContentTag entity = new ContentTag();
        entity.setId(1L);
        entity.setName("Tag A");

        ContentTagDTO dto = mapper.toDto(entity);

        Assertions.assertEquals("Tag A", dto.getName());
    }
}