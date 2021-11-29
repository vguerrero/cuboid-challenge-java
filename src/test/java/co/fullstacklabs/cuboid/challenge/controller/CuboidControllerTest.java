package co.fullstacklabs.cuboid.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import co.fullstacklabs.cuboid.challenge.ApplicationConfig;
import co.fullstacklabs.cuboid.challenge.dto.CuboidDTO;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
class CuboidControllerTest {

    private static final String PATH = "/cuboids";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldUpdateCuboid() throws Exception {
        CuboidDTO cuboidDTO = CuboidDTO.builder().id(1l)
                .width(2f).height(3f).depth(2f).volume(12d).bagId(3L).build();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(cuboidDTO));

        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void invalidInputInUpdateShouldReturnError() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(CuboidDTO.builder().build()));

        this.mockMvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.violations", IsNot.not(IsNull.nullValue())));
    }

    @Test
    void shouldGetErrorWhenCuboidByIdIsEmpty() throws Exception {
        CuboidDTO cuboidDTO = CuboidDTO.builder()
                .width(2f).height(3f).depth(2f).volume(12d).bagId(3L).build();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(cuboidDTO));

        this.mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetErrorOnUpdateWhenBagIdIsNotFound() throws Exception {
        CuboidDTO cuboidDTO = CuboidDTO.builder().id(1l)
                .width(2f).height(3f).depth(2f).volume(12d).build();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(cuboidDTO));

        this.mockMvc.perform(builder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetErrorOnUpdateWhenBagCantProcessCuboidVolumeChange() throws Exception {
        long id = 1L;
        CuboidDTO cuboidDTO = CuboidDTO.builder().id(1l).width(20f).height(5f)
                .depth(5f).volume(50d).bagId(id).build();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(cuboidDTO));

        this.mockMvc.perform(builder)
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void shouldDeleteCuboid() throws Exception {
        long id = 1L;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete(PATH+"/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                ;
        this.mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteWhenCuboidNotFound() throws Exception {
        long id = 529L;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete(PATH+"/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                ;
        this.mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }
    
    /************************************************************
     *                                                           *
     * DO NOT change the tests BELOW, implement the test ABOVE   *
     *                                                           *
    *************************************************************/
    @Test
    void shouldFetchAllCuboids() throws Exception {
        this.mockMvc.perform(get(PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$", IsNot.not(IsNull.nullValue())))
                .andExpect(result -> Assertions.assertThat(
                                result.getResponse().getContentAsString())
                        .contains("\"id\":3,\"width\":3.0,\"height\":3.0,\"depth\":3.0,\"volume\":27.0,\"bagId\":3"));
    }

    @Test
    void shouldCreateNewCuboid() throws Exception {
        CuboidDTO cuboidDTO = CuboidDTO.builder()
                .width(2f).height(3f).depth(2f).volume(12d).bagId(3L).build();

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void invalidInputInPostShouldReturnError() throws Exception {

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CuboidDTO.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.violations", IsNot.not(IsNull.nullValue())));
    }

    @Test
    void shouldGetErrorCreatingWhenBagNotFound() throws Exception {
        CuboidDTO cuboidDTO = CuboidDTO.builder()
                .width(2f).height(3f).depth(2f).volume(12d).bagId(55L).build();

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetErrorCreatingWhenBagCantProcessNewCuboid() throws Exception {
        long id = 1L;
        CuboidDTO cuboidDTO = CuboidDTO.builder().width(20f).height(5f)
                .depth(5f).volume(50d).bagId(id).build();
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
                .andExpect(status().isUnprocessableEntity());
    }
}