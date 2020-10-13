package com.example.vocabulary.domain;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tmt.v20180321.TmtClient;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;

public class Translation {
    public static String translation(String word) {
        try {
            Credential cred = new Credential("AKIDyibDZlmMJzSlnYHmAgwzB6l9CrRYvyGR", "5engZjiZJqzyklm4dx59oDlCMGyHRM13");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            TmtClient client = new TmtClient(cred, "ap-beijing", clientProfile);

            TextTranslateRequest req = new TextTranslateRequest();

            req.setSourceText(word);
            req.setSource("auto");
            req.setTarget("zh");
            req.setProjectId(1259310028L);
            TextTranslateResponse resp = client.TextTranslate(req);
            return resp.getTargetText();
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
