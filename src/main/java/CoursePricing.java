import files.jsonText;
import files.reusableFuntion;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class CoursePricing {
    public static void main(String arg []){
        int sum=0;
        JsonPath js=new JsonPath(jsonText.Coursepricing());

//        1. Print No of courses returned by API
        int count=js.get("courses.size()");
        System.out.println(count);
//        2.Print Purchase Amount
       int totalAmt= js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmt);
//        3. Print Title of the first course
        String course=js.get("courses[0].title");
        System.out.println(course);

//        4. Print All course titles and their respective Prices
        for(int i=0;i<count;i++){
            System.out.println(js.get("courses["+i+"].title").toString());
            System.out.println(js.get("courses["+i+"].price").toString());
        }
//        5. Print no of copies sold by RPA Course
        for(int i=0;i<count;i++){
            String t=(js.get("courses["+i+"].title"));
            if(t.equalsIgnoreCase("RPA")){
                System.out.println("copies of RPA "+js.get("courses["+i+"].copies").toString());
                break;
            }
    }
//        6. Verify if Sum of all Course prices matches with Purchase Amount
        for(int i=0;i<count;i++){
            int price=js.get("courses["+i+"].price");
            int copies=js.get("courses["+i+"].copies");
            int amount=price*copies;
            sum=sum+amount;
        }
        System.out.println(sum);
        Assert.assertEquals(totalAmt,sum);
        System.out.println("Total amount and sum is matched");

}
}
