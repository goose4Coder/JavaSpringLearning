package tacocloud.tacocloud.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tacocloud.tacocloud.dto.IngredientCategory;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.service.IngredientService;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(IngredientCrudController.class)
@AutoConfigureMockMvc(addFilters = false)
public class IngredientCrudControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IngredientService service;

    @Test
    public void testGetAllIngredients_DoesNotFall() throws Exception{
        mockMvc.perform(get("/ingredient/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllIngredients_AccuratelyDisplaysIngredients() throws Exception{
        List<IngredientDto> toTestWith= new ArrayList<IngredientDto>();
        toTestWith.add(IngredientDto.builder().name("Secret sauce").category(IngredientCategory.SAUCE).build());
        toTestWith.add(IngredientDto.builder().name("Wheat tortilla").category(IngredientCategory.BREAD).build());
        when(service.listAll()).thenReturn(toTestWith);
        mockMvc.perform(get("/ingredient/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Secret sauce"))
                .andExpect(jsonPath("$[0].category").value("SAUCE"))
                .andExpect(jsonPath("$[1].name").value("Wheat tortilla"))
                .andExpect(jsonPath("$[1].category").value("BREAD"));
    }


}
