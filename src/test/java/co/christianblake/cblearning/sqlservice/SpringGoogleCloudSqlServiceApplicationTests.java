package co.christianblake.cblearning.sqlservice;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.christianblake.cblearning.sqlservice.repo.CBProfile;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SpringGoogleCloudSqlServiceApplicationTests {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document("{method-name}/{step}/"))
				.build();
	}

	@Test
	public void index() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaTypes.HAL_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("_links.cBProfiles", is(notNullValue())));
	}

	@Test
	public void creatingValid() throws JsonProcessingException, Exception {
		
		Map<String, Object> _profile = new HashMap<String, Object>();
		_profile.put("name", "Bob");
		_profile.put("surname", "Jones");
		_profile.put("email", "bob@jones.com");
		_profile.put("dob", "1982-08-09");
		
		this.mockMvc
		.perform(
				post("/cBProfiles").contentType(MediaTypes.HAL_JSON).content(
						objectMapper.writeValueAsString(_profile)))
		.andExpect(status().isCreated())
		.andExpect(header().string("Location", notNullValue()))
		.andDo(document("creating-valid",
				requestFields(
							fieldWithPath("name").description("first name. e.g. Mark"),
							fieldWithPath("surname").description("surname. e.g. Jones"),
							fieldWithPath("email").description("email address"),
							fieldWithPath("dob").description("date of birth"))))
		.andReturn().getResponse().getHeader("Location");
	}
	
	@Test
	public void creatingInvalid() throws JsonProcessingException, Exception {
		
		CBProfile _profile = new CBProfile();

		this.mockMvc
		.perform(
				post("/cBProfiles").contentType(MediaTypes.HAL_JSON).content(
						objectMapper.writeValueAsString(_profile)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("timestamp", is(notNullValue())))
		.andExpect(jsonPath("status", is("BAD_REQUEST")))
		.andExpect(jsonPath("details", is(notNullValue())))
		.andExpect(jsonPath("messages", is(notNullValue())))
		.andDo(document("creating-invalid",
				responseFields(
						fieldWithPath("messages").description("A list of descriptions of the causes of the error"),
						fieldWithPath("details").description("The path to which the request was made"),
						fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
						fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))))
		.andReturn().getResponse().getHeader("Location");
	}


}
