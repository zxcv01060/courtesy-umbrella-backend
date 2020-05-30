package tw.edu.ntub.imd.courtesyumbrella.util.request;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.InvalidDataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.supplier.DataSupplier;
import tw.edu.ntub.imd.courtesyumbrella.util.request.supplier.RequestSupplier;
import tw.edu.ntub.imd.courtesyumbrella.util.request.verify.ExecuteTimeVerify;
import tw.edu.ntub.imd.courtesyumbrella.util.response.ResponseData;

import java.util.ArrayList;
import java.util.List;

public class TestCreateOrUpdateRequest {
    private final MockMvc mockMvc;
    private final String url;
    private final String successMessage;
    private final DataSupplier dataSupplier;
    private final RequestSupplier requestSupplier;
    private final ExecuteTimeVerify executeTimeVerify;

    private int times;

    public TestCreateOrUpdateRequest(
            MockMvc mockMvc,
            String url,
            String successMessage,
            DataSupplier dataSupplier,
            RequestSupplier requestSupplier,
            ExecuteTimeVerify executeTimeVerify
    ) {
        this.mockMvc = mockMvc;
        this.url = url;
        this.successMessage = successMessage;
        this.dataSupplier = dataSupplier;
        this.requestSupplier = requestSupplier;
        this.executeTimeVerify = executeTimeVerify;
    }

    public void testRequest() throws Exception {
        testValidRequest();
        testNotRequiredDataRequest();
        testInvalidRequest();
    }

    private void testValidRequest() throws Exception {
        sendRequest(dataSupplier.getAllValidDataList());
    }

    private void sendRequest(List<DataParameter> dataParameterList) throws Exception {
        times = times + 1;
        sendRequest(dataParameterList, new ResponseData(true, "", successMessage));
    }

    private void sendRequest(List<DataParameter> dataParameterList, ResponseData responseData) throws Exception {
        mockMvc.perform(requestSupplier.get(url, dataParameterList))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.header()
                        .string("Content-Type", "application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result")
                        .value(responseData.isSuccess()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                        .hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                        .value(responseData.getErrorCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value(responseData.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data")
                        .hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data")
                        .isMap());
        executeTimeVerify.verify(times);
    }

    private void testNotRequiredDataRequest() throws Exception {
        sendRequest(dataSupplier.getRequiredValidDataList());
    }

    private void testInvalidRequest() throws Exception {
        List<DataParameter> allValidDataList = dataSupplier.getAllValidDataList();
        List<InvalidDataParameter> invalidDataList = dataSupplier.getInvalidDataList();
        for (InvalidDataParameter invalidData : invalidDataList) {
            List<DataParameter> testDataList = new ArrayList<>(allValidDataList);
            for (DataParameter testData : testDataList) {
                if (testData.equalWith(invalidData)) {
                    testDataList.remove(testData);
                    break;
                }
            }
            testDataList.add(invalidData);
            sendRequest(testDataList, new ResponseData(
                    false,
                    "FormValidation - Invalid",
                    invalidData.getInvalidMessage()
            ));
        }
    }
}
