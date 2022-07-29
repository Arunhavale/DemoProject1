import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(payload.coursePrice());
		
		// print no of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//print title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//print ALL courses and print title
		for(int i=0;i<count;i++) {
			Object title = js.get("courses["+i+"].title");
			System.out.println(title);
			int price = js.getInt("courses["+i+"].price");
			System.out.println(price);
		}

		//To check no of copies sold by RPA
		System.out.println("Print copies sold by RPA course");
		for(int i=0;i<count;i++) {
			String title = js.get("courses["+i+"].title");
			if(  title.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
		//Verify sum of all course price matches PurchaseAmount
		int sum = 0;
		for(int i=0;i<count;i++) {
			int price = js.get("courses["+i+"].price");
			int copies = js.get("courses["+i+"].copies");
			sum = sum+(price*copies);
		}
		System.out.println("Total purchase amount : " + sum);
		

}
}
