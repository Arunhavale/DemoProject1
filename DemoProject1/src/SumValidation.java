import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {

		JsonPath js = new JsonPath(payload.coursePrice());
		int count = js.getInt("courses.size()");
		System.out.println(count);
		//Verify sum of all course price matches PurchaseAmount
		int sum = 0;
		for(int i=0;i<count;i++) {
			int price = js.get("courses["+i+"].price");
			int copies = js.get("courses["+i+"].copies");
			sum = sum+(price*copies);
		}
		System.out.println("Total purchase amount : " + sum);
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(purchaseAmount, sum);
	}
}
