package com.pwpo.integration;

import com.pwpo.common.model.APIResponse;
import org.springframework.test.web.servlet.MvcResult;

public class IntegrationDataHolder {
    private static IntegrationDataHolder instance;
    private APIResponse apiResponse;
    private MvcResult mvcResult;

    private IntegrationDataHolder() {

    }

    public static MvcResult mvcResult() {
        return getInstance().mvcResult;
    }

    public static APIResponse apiResponse() {
        return getInstance().apiResponse;
    }

    public static void mvcResult(MvcResult mvcResult) {
        getInstance().mvcResult = mvcResult;
    }

    public static void apiResponse(APIResponse apiResponse) {
        getInstance().apiResponse = apiResponse;
    }

    public static void clean() {
        mvcResult(null);
        apiResponse(null);
        instance = null;
    }

    public static IntegrationDataHolder getInstance() {
        if (instance == null) {
            instance = new IntegrationDataHolder();
        }

        return instance;
    }
}
