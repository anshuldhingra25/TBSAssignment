package in.tbsassignment.retrofit;

import java.util.List;

import in.tbsassignment.room.Message;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
 
    @GET("inbox.json")
    Call<List<Message>> getInboxList();

}