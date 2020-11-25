//package server;
//
//import com.suning.api.DefaultSuningClient;
//import com.suning.api.entity.govbus.PreDepositBalanceGetRequest;
//import com.suning.api.entity.govbus.PreDepositBalanceGetResponse;
//import com.suning.api.exception.SuningApiException;
//
//public class GetBalance {
//
//	public static void main(String[] args) {
//		PreDepositBalanceGetRequest request = new PreDepositBalanceGetRequest();
//		//api入参校验逻辑开关，当测试稳定之后建议设置为 false 或者删除该行
//		request.setCheckParam(true);
//		String serverUrl = "https://open.suning.com/api/http/sopRequest";
//		String appKey = "d56a38011b657b6f6a1e7429564895a0";
//		String appSecret = "b326dbb0c40d5f7197dbd42b80ff9f11";
//		DefaultSuningClient client = new DefaultSuningClient(serverUrl, appKey,appSecret, "json");
//		try {
//		 PreDepositBalanceGetResponse response = client.excute(request);
//		 System.out.println("返回json/xml格式数据 :" + response.getBody());
//		} catch (SuningApiException e) {
//		 e.printStackTrace();
//		}
//
//	}
//
//}
